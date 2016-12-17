"use strict";

/*--------------------------------------------
 * Modules
 * -------------------------------------------*/
var express = require('express');
var app = express();
var router = express.Router();
var fs = require('fs');
var jstoxml = require('jstoxml');
var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database('../../familychores.db');
var bodyParser = require('body-parser');
var lastID;

/*--------------------------------------------
 * Variables
 * -------------------------------------------*/
var resultPage = '';
var printToScreen;
var xmlWrapperType = '';

/*--------------------------------------------
 * Settings
 * -------------------------------------------*/
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

/*--------------------------------------------
 * RESTful parts
 * -------------------------------------------*/

/*--------------------------------------------
 * users
 * -------------------------------------------*/
app.get('/users', (req, res)=>{
    printToScreen = res;
    xmlWrapperType = 'user';
    db.serialize(()=>{
        db.all("SELECT * FROM users", [], displayResults);
    });
});

/* Get a user by Id */
app.get('/user/:id', (req, res)=>{
    res.end(req.params.id);
    // TODO FINISH
});

/* The user */
app.route('/user')
    .delete((req, res)=>{
        if(
            !req.body.hasOwnProperty('id') 
        ) {
            res.statusCode = 400;
            return res.send('Error 400: Post syntax incorrect.');
        } 

        printToScreen = res;
        xmlWrapperType = 'delete';
        db.serialize(()=>{
            let stmt = db.prepare("DELETE FROM users WHERE id=?")
            stmt.run(req.body.id).finalize(()=>{
                displayResults(null, req.body.id);
            });
        });
    })

    /* Create the user */
    .post((req, res)=>{
        if(
            !req.body.hasOwnProperty('name') 
        ) {
            res.statusCode = 400;
            return res.send('Error 400: Post syntax incorrect.');
        } 

        printToScreen = res;
        xmlWrapperType = 'user';
        
        try{
            db.serialize(()=>{
                let stmt = db.prepare("INSERT INTO users (name) VALUES (?)");
                stmt.run(req.body.name).finalize(()=>{
                    db.get("SELECT * FROM users WHERE name = ?", req.body.name, displayResults);
                });
            });
        }catch(e){
            res.statusCode = 400;
            return res.send(e);
        }
    })
    // Update the user
    .put((req, res)=>{
        if(
            !req.body.hasOwnProperty('name')
            || !req.body.hasOwnProperty('id')
        ) {
            res.statusCode = 400;
            return res.send('Error 400: Post syntax incorrect.');
        } 

        // To display after the queries are made
        printToScreen = res;
        xmlWrapperType = 'user';
        
        try{
            db.serialize(()=>{
                let stmt = db.prepare("UPDATE users SET name=? WHERE id=?");
                stmt.run(req.body.name, req.body.id).finalize(()=>{
                    db.get("SELECT * FROM users WHERE id = ?", req.body.id, displayResults);
                });
            });
        }catch(e){
            res.statusCode = 400;
            return res.send(e);
        }
    });

/*--------------------------------------------
 * Chores
 * -------------------------------------------*/
app.get('/chores', (req, res)=>{
    printToScreen = res;
    xmlWrapperType = 'chore';
    db.serialize(()=>{
        db.all(
            "SELECT c.id, c.name, c.description, c.duedate, c.completed, ac.userid FROM chores c LEFT JOIN assignedchores ac ON c.id = ac.choresid", [], displayResults);
    });
});

/* Get a chore by Id */
app.get('/chore/:id', (req, res)=>{
    res.end(req.params.id);
    // TODO FINISH
});

/* The chore */
app.route('/chore')
    .delete((req, res)=>{
        if(
            !req.body.hasOwnProperty('id') 
        ) {
            res.statusCode = 400;
            return res.send('Error 400: Post syntax incorrect.');
        } 

        printToScreen = res;
        xmlWrapperType = 'delete';
        db.serialize(()=>{
            let stmt = db.prepare("DELETE FROM chores WHERE id=?")
            stmt.run(req.body.id).finalize(()=>{
                displayResults(null, req.body.id);
            });
        });
    })

    /* Create the chore */
    .post((req, res)=>{
        if(
            !req.body.hasOwnProperty('name')
            || !req.body.hasOwnProperty('description')
            || !req.body.hasOwnProperty('completed')
            || !req.body.hasOwnProperty('duedate')
            || !req.body.hasOwnProperty('userId')
            
        ) {
            res.statusCode = 400;
            return res.send('Error 400: Post syntax incorrect.');
        } 

        printToScreen = res;
        xmlWrapperType = 'chore';
        
        try{
            db.serialize(()=>{
                let stmt = db.prepare(
                    "INSERT INTO chores (name, description, completed, duedate) VALUES (?, ?, ?, ?)");
                stmt.run(req.body.name, req.body.description, req.body.completed, req.body.duedate).finalize(()=>{
                    let stmt_assoc = db.prepare(
                        "INSERT INTO assignedchores (userid, choresid) VALUES (?, ?)");
                    db.get("SELECT * FROM chores WHERE name = ?", req.body.name, (e,r)=>{
                        displayResults(e,r);
                        db.get("SELECT id FROM chores WHERE name = ?", req.body.name, (e,r)=>{
                            stmt_assoc.run(req.body.userId, r.id);
                        });
                    })
                    
                });
            });
        }catch(e){
            res.statusCode = 400;
            return res.send(e);
        }
    })
    // Update the chore
    .put((req, res)=>{
        if(
            !req.body.hasOwnProperty('id')
            || !req.body.hasOwnProperty('name')
            || !req.body.hasOwnProperty('description')
            || !req.body.hasOwnProperty('completed')
            || !req.body.hasOwnProperty('duedate')
            || !req.body.hasOwnProperty('userId')
        ) {
            res.statusCode = 400;
            return res.send('Error 400: Post syntax incorrect.');
        } 

        // To display after the queries are made
        printToScreen = res;
        xmlWrapperType = 'chore';
        
        try{
            db.serialize(()=>{
                let stmt = db.prepare(
                    "UPDATE chores SET name=?, description=?, completed=?, duedate=? WHERE id=?");
                stmt.run(
                    req.body.name, 
                    req.body.description,
                    req.body.completed,
                    req.body.duedate,
                    req.body.id
                ).finalize(()=>{
                    let stmt_assoc = db.prepare(
                        "UPDATE assignedchores SET userid=? WHERE choresid=?");
                    stmt_assoc.run(req.body.userId, req.body.id);
                    db.get("SELECT * FROM chores WHERE id = ?", req.body.id, displayResults);
                });
            });
        }catch(e){
            res.statusCode = 400;
            return res.send(e);
        }
    });
    

/*--------------------------------------------
 * Working parts
 * -------------------------------------------*/

/**
 * Will display any results to the screen 
 */
var displayResults = (e, r) => {
    let _tmp = {};
    _tmp[xmlWrapperType+'s'] = wrapWithType(xmlWrapperType, r);
    printToScreen.end(
        jstoxml.toXML(
            _tmp, 
            {header:true}
        )
    );
}

/**
 * Will wrap the values into a type to be better suited for XML consumption.
 */
var wrapWithType = (type, r) => {
    let _return = [];

    if(toType(r) == 'array'){
        r.forEach(function(element) {
            let _wrap = {};
            _wrap[type] = element;
            _return.push(_wrap);
        }, this);
    }else{
        let _wrap = {};
        _wrap[type] = r;
        _return.push(_wrap);
    }

    return _return;
}

/**
 * This is a helper method to show accuratly the type of variable.
 */
var toType = function(obj) {
  return ({}).toString.call(obj).match(/\s([a-zA-Z]+)/)[1].toLowerCase()
}

/**
 * The server
 */
var server = app.listen(8080, ()=>{
    var host = server.address().address;
    var port = server.address().port;
    console.log('Run at localhost:8080');
});

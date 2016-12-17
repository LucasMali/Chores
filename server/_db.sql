CREATE TABLE users(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL
);

CREATE TABLE chores(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(10) NOT NULL,
  description TEXT,
  completed TINYINT(1) DEFAULT 0,
  duedate VARCHAR(10) NOT NULL /* long unixTime = System.currentTimeMillis() / 1000L; */
);

CREATE TABLE assignedchores(
  userid INTEGER,
  choresid INTEGER,
  FOREIGN KEY(userid) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY(choresid) REFERENCES chores(id) ON DELETE CASCADE
);
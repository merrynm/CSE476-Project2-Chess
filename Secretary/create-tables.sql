CREATE TABLE players (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name TEXT,
    password TEXT
);

CREATE TABLE game (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    player_1 varchar(32),
    player_2 varchar(32),
    turn INT,
    timeout timestamp,
    end_game false,
    Unique(player_1)
);

CREATE TABLE pieces (
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    player_color TEXT,
    piece_type TEXT,
    board_row_x FLOAT,
    board_col_y FLOAT,
    unique(id)
);

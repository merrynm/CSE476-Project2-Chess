<?php
    /*
     * Login for the server.
     * POST Request
     * We are getting two parameters: id and password
     */
    require_once "db.inc.php";
    echo '<?xml version="1.0" encoding="UTF-8" ?>';

    if(!isset($_POST['xml'])) {
        echo '<secretary status="no" msg="missing XML" />';
        echo implode($_POST);
        exit;
    }
    processXML(stripslashes($_POST['xml']));

    $pdo = pdo_connect();

    function processXML($xmltext) {
        // Load the XML
        $xml = new XMLReader();
        if(!$xml->XML($xmltext)) {
            echo '<secretary status="no" msg="invalid XML" />';
            exit;
        }

        $pdo = pdo_connect();

        // Read to the start tag
        while($xml->read()) {
            if($xml->nodeType == XMLReader::ELEMENT && $xml->name == "secretary") {
                $username = $xml->getAttribute("username");
                $password = $xml->getAttribute("password");
                validateUser($pdo, $username, $password);
            }
        }
    }

function validateUser($pdo, $username, $password) {
    $usernameQ = $pdo->quote($username);
    $query = "SELECT id, password from players where players.name =$usernameQ";

    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        // check password for record
        if($row['password'] != $password) {
            echo '<secretary status="no" msg="password error" />';
            exit;
        } else {
           // echo '<secretary status="yes" msg="success" />';
            gameCheck($pdo,$username);
            exit;
        }
    }
    echo '<secretary status="no" msg="user error" />';
    exit;
}

function gameCheck($pdo, $username){
    //check for valid game
    $query = "SELECT * from game";
    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        //there was something
        if($row['player_1'] == $username || $row['player_2'] == $username ) {
            echo '<secretary status="yes" msg="already playing" new_game="false" />';
            exit;
        }
        if ($row['player_2'] == "null") {
            //insert user as player 2
            $player_1 = $row['player_1'];
            $player_1Q = $pdo->quote($player_1);
            $player_2 = $pdo->quote($username);
            $turn = 1;
            $timeout = $row['timeout'];
            $end_game = $row['end_game'];
            $query_2 = <<<QUERY
REPLACE INTO game(player_1, player_2, turn)
VALUES($player_1Q,$player_2, $turn)
QUERY;
            if (!$pdo->query($query_2)) {
                echo '<secretary status="no" msg="insertfail1">';
                //echo '<secretary status="no" msg="insertfail">' . $query . '</secretary>';
                exit;
            }else {
                echo '<secretary status="yes" msg="added second player" new_game="false" />';
                exit;
                
            }
        } else{
            //game is full
            echo '<secretary status="no" msg="game is full" />';
            exit;
        }
    }else{
        //create new game and set user as player 1
        $usernameQ = $pdo->quote($username);
        $query = "insert into game(player_1, player_2, turn) values($usernameQ,'null',2)";
        if (!$pdo->query($query)) {
            echo '<secretary status="no" msg="insertfail2">';
          //  echo '<secretary status="no" msg="insertfail">' . $query . '</secretary>';
            exit;
        }else{
            $game_success = true;
            echo '<secretary status="yes" msg="created new game" new_game="true" />';
        }
    }
}

?>
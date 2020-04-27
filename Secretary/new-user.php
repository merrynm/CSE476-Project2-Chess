<?php
/*
 * Creates a new user
 * Method: Post
 * username and password
 * returns success or failure
 */
require_once "db.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

if(!isset($_POST['xml'])) {
    echo '<secretary status="no" msg="missing XML" />';
    echo implode($_POST);
    exit;
}
processXML(stripslashes($_POST['xml']));

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
            createNewUser($pdo, $username, $password);
        }
    }
}

/**
 * Creates a new user with the given username and password
 * checks to ensure there is not a duplicate username
 * @param $pdo pdo database connection
 * @param $username username new user username
 * @param $password password new user password
 */
function createNewUser($pdo, $username, $password) {
    $usernameQ = $pdo->quote($username);
    $query = "SELECT id from players where players.name =$usernameQ";

    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        echo '<secretary status="no" msg="user exists" />';
        exit;
    } else {
        $passwordQ = $pdo->quote($password);
        $query = "INSERT INTO players(name, password) VALUES($usernameQ, $passwordQ);";
        $pdo->query($query);
        echo '<secretary status="yes" msg="user created" />';
        exit;
    }
}
?>


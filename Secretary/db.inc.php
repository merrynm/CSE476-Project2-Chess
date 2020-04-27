<?php
function pdo_connect()
{
    try {
        // Production server
        $dbhost = "mysql:host=mysql-user.cse.msu.edu;dbname=schwagl3";
        $user = "schwagl3";
        $password = "Z3dM3m3*";
        return new PDO($dbhost, $user, $password);
    } catch (PDOException $e) {
        die("Unable to select database");
    }
}
?>
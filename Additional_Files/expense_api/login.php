<?php
require 'db_connect.php';
$email = $_POST['email'];
$pass = $_POST['password'];

$sql = "SELECT * FROM users WHERE email='$email' AND password='$pass'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    echo json_encode([
        "status" => "success", 
        "message" => "Login success", 
        "user" => ["id" => $row['id'], "full_name" => $row['full_name'], "email" => $row['email']]
    ]);
} else {
    echo json_encode(["status" => "error", "message" => "Invalid credentials"]);
}
?>
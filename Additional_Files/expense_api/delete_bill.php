<?php
error_reporting(0);
header('Content-Type: application/json; charset=utf-8');
require 'db_connect.php';

$id = $_POST['id'];

$stmt = $conn->prepare("DELETE FROM bills WHERE id = ?");
$stmt->bind_param("i", $id);

if ($stmt->execute()) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error"]);
}
?>
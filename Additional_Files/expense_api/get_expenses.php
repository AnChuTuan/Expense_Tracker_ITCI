<?php
require 'db_connect.php';
$uid = $_GET['user_id'];

$sql = "SELECT * FROM expenses WHERE user_id='$uid' ORDER BY date DESC";
$result = $conn->query($sql);

$expenses = array();
while($row = $result->fetch_assoc()) {
    $expenses[] = $row;
}
echo json_encode(["status" => "success", "data" => $expenses]);
?>
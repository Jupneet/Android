<?php
 
$path = "/var/www/androidData/data/"; 
if (isset($_FILES['image']['name'])) {
    $tpath = $path . basename($_FILES['image']['name']);

    try {
        
        if (!move_uploaded_file($_FILES['image']['tmp_name'], $path)) {
            
        }

    } catch (Exception $e) {
        
    }
} else {

}
echo json_encode($response);
?>



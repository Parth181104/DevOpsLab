<?php 
// Database credentials
$host = "localhost";
$user = "mahimakela";
$pass = "ma@2815@2815";
$db = "login";

// Establishing connection
$conn = new mysqli($host, $user, $pass, $db);

// Check for connection errors
if($conn->connect_error){
    die("Failed to connect to DB: " . $conn->connect_error);
} else {
    echo "Connected successfully";
}

// Check if the signUp form was submitted
if(isset($_POST['signUp'])){
    $firstName = $_POST['fName'];
    $lastName = $_POST['lName'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // Hash password using bcrypt
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    // Prepared statement to check if email already exists
    $checkEmail = $conn->prepare("SELECT * FROM users WHERE email = ?");
    $checkEmail->bind_param('s', $email);
    $checkEmail->execute();
    $result = $checkEmail->get_result();

    if($result->num_rows > 0){
        echo "Email Address Already Exists!";
    } else {
        // Prepared statement for inserting new user
        $insertQuery = $conn->prepare("INSERT INTO users (firstName, lastName, email, password) VALUES (?, ?, ?, ?)");
        $insertQuery->bind_param('ssss', $firstName, $lastName, $email, $hashedPassword);

        if($insertQuery->execute()){
            header("location: login.html");
            exit();
        } else {
            echo "Error: " . $conn->error;
        }
    }
}

// Check if the signIn form was submitted
if(isset($_POST['signIn'])){
    $email = $_POST['email'];
    $password = $_POST['password'];

    // Prepared statement to check email
    $sql = $conn->prepare("SELECT * FROM users WHERE email = ?");
    $sql->bind_param('s', $email);
    $sql->execute();
    $result = $sql->get_result();

    if($result->num_rows > 0){
        session_start();
        $row = $result->fetch_assoc();
        
        // Verify password
        if(password_verify($password, $row['password'])){
            $_SESSION['email'] = $row['email'];
            header("Location: homepage.php");
            exit();
        } else {
            echo "Incorrect Password!";
        }
    } else {
        echo "Email not found!";
    }
}

// Close the connection
$conn->close();
?>

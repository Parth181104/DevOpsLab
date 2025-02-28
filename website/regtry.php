<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title> <!--changes made by Karthik Reddy -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Poppins", sans-serif;
        }
        body {
            background-color: #ff4081;
            background: linear-gradient(to right, #e2e2e2, #ff4081);
        }
        .container {
            background: #fff;
            width: 450px;
            padding: 1.5rem;
            margin: 50px auto;
            border-radius: 10px;
            box-shadow: 0 20px 35px rgba(0, 0, 1, 0.9);
        }
        form {
            margin: 0 2rem;
        }
        .form-title {
            font-size: 1.5rem;
            font-weight: bold;
            text-align: center;
            padding: 1.3rem;
            margin-bottom: 0.4rem;
        }
        input {
            color: inherit;
            width: 100%;
            background-color: transparent;
            border: none;
            border-bottom: 1px solid #757575;
            padding-left: 1.5rem;
            font-size: 15px;
        }
        .input-group {
            padding: 1% 0;
            position: relative;
        }
        .input-group i {
            position: absolute;
            color: black;
        }
        input:focus {
            background-color: transparent;
            outline: transparent;
            border-bottom: 2px solid hsl(327, 90%, 28%);
        }
        .btn {
            font-size: 1.1rem;
            padding: 8px 0;
            border-radius: 5px;
            outline: none;
            border: none;
            width: 100%;
            background: rgb(235, 125, 230);
            color: white;
            cursor: pointer;
            transition: 0.9s;
        }
        .btn:hover {
            background: #07001f;
        }
    </style>
</head>
<body>

<div class="container" id="signup">
    <h1 class="form-title">Register</h1>
    <form method="post" action="">
        <div class="input-group">
            <i class="fas fa-user"></i>
            <input type="text" name="firstName" id="FirstName" placeholder="First Name" required>
            <label for="FirstName">First Name</label>
        </div>
        <div class="input-group">
            <i class="fas fa-user"></i>
            <input type="text" name="lastName" id="lastName" placeholder="Last Name" required>
            <label for="lastName">Last Name</label>
        </div>
        <div class="input-group">
            <i class="fas fa-envelope"></i>
            <input type="email" name="email" id="email" placeholder="Email" required>
            <label for="email">Email</label>
        </div>
        <div class="input-group">
            <i class="fas fa-lock"></i>
            <input type="password" name="password" id="password" placeholder="Password" required>
            <label for="password">Password</label>
        </div>
        <div class="input-group">
            <i class="fas fa-lock"></i>
            <input type="password" name="confirm_password" id="confirm_password" placeholder="Confirm Password" required>
            <label for="confirm_password">Confirm Password</label>
        </div>
        <input type="submit" class="btn" value="Sign Up" name="signUp">
    </form>
</div>
</body>
</html>

<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve form data and sanitize inputs
    $firstName = htmlspecialchars(trim($_POST['firstName']));
    $lastName = htmlspecialchars(trim($_POST['lastName']));
    $email = htmlspecialchars(trim($_POST['email']));
    $password = $_POST['password'];
    $confirm_password = $_POST['confirm_password'];

    // Check if all fields are filled
    if (empty($firstName) || empty($lastName) || empty($email) || empty($password) || empty($confirm_password)) {
        die("<script>alert('All fields are required.');</script>");
    }

    // Validate email format
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        die("<script>alert('Invalid email format.');</script>");
    }

    // Check if passwords match
    if ($password !== $confirm_password) {
        die("<script>alert('Passwords do not match.');</script>");
    }

    // Validate password strength (minimum 8 characters, one uppercase, one number)
    if (!preg_match("/^(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/", $password)) {
        die("<script>alert('Password must be at least 8 characters long, contain one uppercase letter, and one number.');</script>");
    }

    // Hash the password for security
    $hashedPassword = password_hash($password, PASSWORD_BCRYPT);

    // Database connection details
    $host = "localhost";
    $user = "mahimakela";
    $pass = "ma@2815@2815";
    $db = "login";

    // Secure database connection using PDO
    try {
        $conn = new PDO("mysql:host=$host;dbname=$db;charset=utf8", $user, $pass);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Check if email already exists
        $checkStmt = $conn->prepare("SELECT email FROM users WHERE email = :email");
        $checkStmt->bindParam(":email", $email);
        $checkStmt->execute();
        if ($checkStmt->rowCount() > 0) {
            die("<script>alert('Email already registered. Please use a different email.');</script>");
        }

        // Use a prepared statement to prevent SQL Injection
        $stmt = $conn->prepare("INSERT INTO users (FirstName, lastName, email, password) VALUES (:firstName, :lastName, :email, :password)");
        $stmt->bindParam(":firstName", $firstName);
        $stmt->bindParam(":lastName", $lastName);
        $stmt->bindParam(":email", $email);
        $stmt->bindParam(":password", $hashedPassword);

        if ($stmt->execute()) {
            echo "<script>
                alert('Registration successful! Thank you for signing up.');
                window.location.href = 'index.php';
            </script>";
        } else {
            echo "<script>alert('Error: Could not register. Please try again.');</script>";
        }
    } catch (PDOException $e) {
        echo "<script>alert('Database Error: " . $e->getMessage() . "');</script>";
    }

    // Close connection
    $conn = null;
}
?>

</body>
</html>

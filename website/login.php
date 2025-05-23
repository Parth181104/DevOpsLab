<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register & Login</title> <!-- changes made by Aniket Pangavhane-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <style>  
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Poppins", sans-serif;
        }
        body {
            background: linear-gradient(to right, #e2e2e2, #ff4081);
        }
        .container {
            background: #fff;
            width: 450px;
            padding: 1.5rem;
            margin: 50px auto;
            border-radius: 10px;
            box-shadow: 0 20px 35px rgba(0, 0, 1, 0.9);
            text-align: center;
        }
        .form-title {
            font-size: 1.5rem;
            font-weight: bold;
            padding: 1.3rem;
        }
        .input-group {
            margin: 10px 0;
            position: relative;
        }
        .input-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #757575;
            border-radius: 5px;
            font-size: 16px;
        }
        .btn {
            font-size: 1.1rem;
            padding: 10px;
            width: 100%;
            border-radius: 5px;
            border: none;
            background: rgb(235, 125, 230);
            color: white;
            cursor: pointer;
            transition: 0.3s;
        }
        .btn:hover {
            background: #07001f;
        }
        .toggle {
            margin-top: 10px;
            color: blue;
            cursor: pointer;
        }
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
    <!-- Sign In Form -->
    <div class="container" id="signIn">
        <h1 class="form-title">Sign In</h1>
        <form method="post" action="login.php">
            <div class="input-group">
                <input type="email" name="email" placeholder="Email" required>
            </div>
            <div class="input-group">
                <input type="password" name="password" placeholder="Password" required>
            </div>
            <input type="submit" class="btn" value="Sign In" name="signIn">
        </form>
        <p class="toggle" id="showSignUp">Don't have an account? Sign Up</p>
    </div>

    <!-- Sign Up Form -->
    <div class="container hidden" id="signUp">
        <h1 class="form-title">Sign Up</h1>
        <form method="post" action="register.php">
            <div class="input-group">
                <input type="text" name="FirstName" placeholder="First Name" required>
            </div>
            <div class="input-group">
                <input type="text" name="lastName" placeholder="Last Name" required>
            </div>
            <div class="input-group">
                <input type="email" name="email" placeholder="Email" required>
            </div>
            <div class="input-group">
                <input type="password" name="password" placeholder="Password" required>
            </div>
            <input type="submit" class="btn" value="Sign Up" name="signUp">
        </form>
        <p class="toggle" id="showSignIn">Already have an account? Sign In</p>
    </div>

<script>
    const signUpButton = document.getElementById('signUpButton');
    const signInButton = document.getElementById('signInButton');
    const signInForm = document.getElementById('signIn');
    const signUpForm = document.getElementById('signup');

    signUpButton.addEventListener('click', function (event) {
        event.preventDefault();  // Prevent form submission
        signInForm.style.display = "none";
    });

    signInButton.addEventListener('click', function (event) {
        event.preventDefault();  // Prevent form submission
        signInForm.style.display = "block";
    });
</script>
<?php 

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve form data
    $firstName = isset($_POST['FirstName']) ? $_POST['FirstName'] : null;
    $lastName = isset($_POST['lastName']) ? $_POST['lastName'] : null;
    $email = isset($_POST['email']) ? $_POST['email'] : null;
    $password = isset($_POST['password']) ? $_POST['password'] : null;

    // Validate inputs
    if (empty($firstName) || empty($lastName) || empty($email) || empty($password)) {
    }

    // Hash the password
    $hashedPassword = password_hash($password, PASSWORD_BCRYPT);

    // Database connection details
    $host = "localhost";
    $user = "mahimakela";
    $pass = "ma@2815@2815";
    $db = "login";

    // Create a database connection
    $conn = new mysqli($host, $user, $pass, $db);

    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // Use prepared statement to insert data
    $stmt = $conn->prepare("INSERT INTO users (FirstName, lastName, email, password) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $firstName, $lastName, $email, $hashedPassword);

    if (empty($firstName) || empty($lastName) || empty($email) || empty($password)) {
        echo "<script>alert('All fields are required.'); window.location.href = 'index.php';</script>";
        exit();
    }
    
    // Close statement and connection
    $stmt->close();
    $conn->close();
}
?>
</body>
</html>

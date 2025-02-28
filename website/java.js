document.addEventListener("DOMContentLoaded", function () {
    const menuBtn = document.querySelector(".menu-btn");
    const menu = document.querySelector(".navbar .menu");
    const menuIcon = document.querySelector(".menu-btn i");

    // Toggle the navbar menu visibility on mobile
    menuBtn.addEventListener("click", function () {
        menu.classList.toggle("active");
        menuIcon.classList.toggle("active");
    });

    // Close menu when clicking outside
    document.addEventListener("click", function (event) {
        if (!menu.contains(event.target) && !menuBtn.contains(event.target)) {
            menu.classList.remove("active");
            menuIcon.classList.remove("active");
        }
    });

    // Ensure smooth transition effect
    menu.style.transition = "all 0.3s ease-in-out";

    // Added by Karthik Reddy & Modified with Enhancements
});

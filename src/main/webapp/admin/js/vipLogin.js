document.addEventListener('DOMContentLoaded', function () {
	console.log('i am running')
	
	
    const emailError = document.getElementById('email-error');
    const emailField = document.getElementById('email');
    const passwordField = document.getElementById('password');
	
    const loginButton = document.getElementById('register-form');

    loginButton.addEventListener('submit', async function (event) {
        event.preventDefault();
		const role = document.getElementById('role').value.trim();
        // Clear previous error messages
        emailError.innerText = "";
		
        const username = emailField.value.trim();
        const password = passwordField.value.trim();

        if (!username || !password) {
            alert("Please fill in both email and password.");
            return;
        }

        const formData = {role, username, password };

        try {
            const response = await fetch("http://localhost:8080/complaint-management/vipLogin", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData),
				credentials: "include"
            });

			const text = await response.text(); // Read raw text safely
			   let data = {};
			   try {
			       data = JSON.parse(text); // Try parsing JSON
			   } catch (e) {
			       console.warn("Invalid JSON response:", e);
			   }
			if (response.ok) {
			        if (data.message === "valid") {
			            window.location.href = "http://localhost:8080/complaint-management/admin/adminDashboard.jsp";
			        } else {
			            emailError.innerText = data.message || "Invalid login.";
			        }
			    } else {
			        
			        emailError.innerText = data.message || "Login failed. Please check your input.";
			    }

			} catch (error) {
			    console.error("Error:", error);
			    emailError.innerText = "Server error. Please try again later.";
			}
    });
});

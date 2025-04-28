document.addEventListener('DOMContentLoaded', function () {
	const submitBtn = document.getElementById('btn');
	submitBtn.addEventListener('click', async function (event) {
		event.preventDefault();

		const firstNameField = document.getElementById('fname');
		const lastNameField = document.getElementById('lname');
		const emailField = document.getElementById("email");
		const phoneField = document.getElementById('phone');
		const departmentField = document.getElementById('dept');
		const passwordField = document.getElementById("password");

		const fname = firstNameField.value.trim();
		const lname = lastNameField.value.trim();
		const email = emailField.value.trim();
		const phone = phoneField.value.trim();
		const dept = departmentField.value.trim();
		const password = passwordField.value.trim();

		// Log values to debug
		console.log("fname:", fname);
		console.log("lname:", lname);
		console.log("email:", email);
		console.log("phone:", phone);
		console.log("password:", password);
		console.log("dept:", dept);

		try {
			const formdata = {
				firstname: fname,
				lastname: lname,
				email: email,
				phone: phone,
				pass: password,
				dept: dept
			};

			let response = await fetch("http://localhost:8080/complaint-management/CreateAdmin", {
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(formdata)
			});

			let data = await response.json();
			

			if (data.message === "success") {
				firstNameField.value = "";
				lastNameField.value = "";
				emailField.value = "";
				phoneField.value = "";
				departmentField.value = "";
				passwordField.value = "";
				alert("✅ Admin Successfully created.");
			} else {
				alert("⚠️ Admin is not created. Please try again.");
			}
		} catch (error) {
			alert("Server error: request failed");
			console.error("Server request failed: ", error);
		}
	});
});


document.addEventListener('DOMContentLoaded', function() {
	const submitBtn = document.getElementById("btn");

	submitBtn.addEventListener('click', async function(event) {
		event.preventDefault();

		const titleField= document.getElementById('title');
		const priorityField = document.getElementById('priority');
		const descriptionField = document.getElementById("description");
		
		const title= titleField.value;
		const priority = priorityField.value;
		const description = descriptionField.value;
		try {
			const formdata = { title, priority, description };
			let response = await fetch("http://localhost:8080/complaint-management/registerComplaint", {
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(formdata)
			});

			let data = await response.json();

			if (data.message === "success") {
				titleField.value=" ";
				descriptionField.value=" ";
				window.location.href = "http://localhost:8080/complaint-management/dashboard.jsp";
				
			} else {
				alert("Failed to register complaint: " + data.message || data.error);
			}
			console.log(data);
		} catch (error) {
			alert("Server error: request failed");
			console.error("Server request failed: ", error);
		}
	});
});

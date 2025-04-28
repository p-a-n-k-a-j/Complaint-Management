
window.onload = async function () {
   profileData();
	
};
async function profileData(){
	const firstName = document.getElementById('firstname');
		const lastName = document.getElementById('lastname');
		const email = document.getElementById('emailid');
		const mobile = document.getElementById('mobile');
		const dept = document.getElementById('dept');
		const role = document.getElementById('role');
	    try {
	        let response = await fetch("http://localhost:8080/complaint-management/ProfileInfo", {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	            }
	           
	        });

	        if (response.ok) {
	            let data = await response.json();
	            if (data && Object.keys(data).length > 0) {
					
					
	                firstName.innerText=data.firstName;
					lastName.innerText = data.lastName;
					email.innerText = data.email;
					role.innerText = data.role;
					dept.innerText = data.depart;
					mobile.innerText = data.phone;
	            } else {
	                alert("No data returned ❗");
	            }
	        } else {
	            console.error("Server responded with status:", response.status);
	            alert("Server error: " + response.status);
	        }

	    } catch (error) {
	        console.error("Server error while fetching data:", error);
	        alert("Server error! Unable to fetch data.");
	    }
}

document.getElementById("edit").addEventListener("click", () => {
  // Hide profile view, show form
  document.getElementById("profile-view").style.display = "none";
  document.getElementById("edit-form").style.display = "block";

  // Pre-fill form fields from profile info
  document.getElementById("fname").value = document.getElementById("firstname").innerText;
  document.getElementById("lname").value = document.getElementById("lastname").innerText;
  document.getElementById("phone").value = document.getElementById("mobile").innerText;
});
document.getElementById('close').addEventListener('click', function(){
	document.getElementById("profile-view").style.display = "block";
	 document.getElementById("edit-form").style.display = "none";
});

document.getElementById('btn').addEventListener('click', async function (event) {
    event.preventDefault(); // Prevent default form submission

    const form = document.getElementById("update-form");
    const formDataObj = Object.fromEntries(new FormData(form).entries());

    const { firstName, lastName, phoneNumber } = formDataObj;

    

    // Validation check
    if (!firstName || !lastName || !phoneNumber) {
        alert("Please fill all data");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/complaint-management/ProfileUpdate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formDataObj)
        });

        const data = await response.json();
        

        if (data && data.message === "success") {
            document.getElementById("profile-view").style.display = "block";
            document.getElementById("edit-form").style.display = "none";
			profileData();
        } else {
            alert("⚠️ Sorry! Data is not updated");
        }
    } catch (error) {
        console.error("Error while updating profile:", error);
        alert("Server error");
    }
});

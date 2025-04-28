//define error section
		const fname_error =	document.getElementById("fname-error"); 
		const lname_error =	document.getElementById("lname-error");
		const email_error = document.getElementById('email-error');
		const verify_error = document.getElementById('verify_error');
		const pass_error = document.getElementById('password-error');
		const phone_error = document.getElementById("phone-error");
function clearErrors() {
    const errorFields = [
        { inputId: "fname", errorId: "fname-error" },
        { inputId: "lname", errorId: "lname-error" },
        { inputId: "email", errorId: "email-error" },
        { inputId: "verificationkey", errorId: "verify_error" },
        { inputId: "password", errorId: "pass-error" },
        { inputId: "phone", errorId: "phone-error" }
    ];

    errorFields.forEach(field => {
        const inputEl = document.getElementById(field.inputId);
        const errorEl = document.getElementById(field.errorId);
        if (inputEl) inputEl.style.borderColor = "#ccc"; // Reset border
        if (errorEl) errorEl.innerText = ""; // Clear error text
    });
	
	function clearSingleError(fieldId) {
	    document.getElementById(fieldId + "_error").innerText = "";
	    document.getElementById(fieldId).style.borderColor = "#ccc";
	}

}


document.getElementById("role").addEventListener("change", function () {
    const role = this.value;
	
    const labelField = document.getElementById("dept-label");
    const roleField = document.getElementById("role-specific-field");

    if (labelField) {
        labelField.innerText = role === "student" ? "Branch" : "Department";
    }
    if (roleField) {
        roleField.style.display = "flex";
    }
});



let varificationKey;
let verifiedEmail = null;

const verifyInputDiv = document.getElementById('verify-input');
const getOtpBtn = document.getElementById("otp");
const emailInput = document.getElementById("email");
const checkIcon = document.getElementById("check");
const verifyBtn = document.getElementById("verify");

//here initially check button
checkIcon.style.display = "none";


		


getOtpBtn.addEventListener("click", async function (event) {
    event.preventDefault();
	clearErrors()
	
    const fname = document.getElementById('fname').value.trim();
    const email = emailInput.value.trim();

    if (!email || !fname) {
		emailInput.style.borderColor="red";
        email_error.innerText="Please enter both name and email";
        return;
    }
	const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	if (!emailPattern.test(email)) {
	    emailInput.style.borderColor = "red";
	    email_error.innerText = "Please enter a valid email address";
	    return;
	}


    verifyInputDiv.style.display = "block";
    checkIcon.style.display = "none"; // reset check
    document.getElementById("verificationkey").value = "";
    document.getElementById("verificationkey").style.borderColor = "#ccc";

    const data = {
        fname: fname,
        email: email
    };

    try {
        const response = await fetch("http://localhost:8080/complaint-management/varify", {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (result && result.message) {
            varificationKey = result.message;
        } else {
			emailInput.style.borderColor="red";
            email_error.innerText="Something went wrong while getting key.";
        }

    } catch (error) {
        console.error("Error:", error);
       email_error.innerText="Request failed.";
    }
});


verifyBtn.addEventListener("click", function () {
	clearErrors()
    const userInputKey = document.getElementById('verificationkey').value.trim();
    const userInput = document.getElementById('verificationkey');

    if (varificationKey !== userInputKey) {
        userInput.style.borderColor = "red";
        verify_error.innerText="Invalid key entered";
    } else {
        verifiedEmail = emailInput.value.trim();
        checkIcon.style.display = 'block';
        verifyInputDiv.style.display = 'none';
        getOtpBtn.style.display = "none";
    }
});

emailInput.addEventListener("input", function () {
    const currentEmail = this.value.trim();

    if (verifiedEmail && currentEmail !== verifiedEmail) {
        checkIcon.style.display = "none";
        getOtpBtn.style.display = "block";
        verifyInputDiv.style.display = "none";
        document.getElementById("verificationkey").value = "";
        document.getElementById("verificationkey").style.borderColor = "#ccc";
        verifiedEmail = null;
    }
});

//name  validation
const namePattern = /^[A-Za-z\s]+$/;

document.getElementById("fname").addEventListener("input", function () {
    const fname = this.value.trim();

    if (fname === "") {
        clearErrors(); // User removed all text, so clear error
        this.style.borderColor = "#ccc";
        return;
    }

    if (fname.length < 2 || !namePattern.test(fname)) {
        fname_error.innerText = "First name must be 2+ letters, alphabets only.";
        this.style.borderColor = "red";
    } else {
        clearErrors();
        this.style.borderColor = "#ccc";
    }
});

document.getElementById("lname").addEventListener("input", function () {
    const lname = this.value.trim();

	if (lname === "") {
	     clearErrors(); // User removed all text then removed
	     this.style.borderColor = "#ccc";
	     return;
	 }

	 if (lname.length < 2 || !namePattern.test(lname)) {
	     lname_error.innerText = "First name must be 2+ letters, alphabets only.";
	     this.style.borderColor = "red";
	 } else {
	     clearErrors();
	     this.style.borderColor = "#ccc";
	 }
});

//end name validation


// Password validation started here

//these are for treak user input these or not
let hasLower = false;
let hasUpper = false;
let hasNumber = false;
let hasSpecial = false;
let hasLength = false;


// These are all pass config divs
const lower = document.getElementById("lower");
const upper = document.getElementById("upper");
const spacial = document.getElementById("spacial");
const character = document.getElementById("character");
const num = document.getElementById("num");

// Container for password config
const pass_config = document.getElementById("pass-config");

// For setting dynamic height
const changeHeight = document.getElementById("pass-errors");


document.getElementById("password").addEventListener('focus', function(){pass_error.innerText ="";});
document.getElementById("password").addEventListener("input", function () {
	const fieldData = this.value.trim();
	//Reseting color
	lower.style.color = upper.style.color = spacial.style.color = num.style.color = character.style.color = "red";

	// Reset booleans
	hasLower = hasUpper = hasNumber = hasSpecial = hasLength = false;

	// Hide config if input is empty
	if (fieldData === "") {
		pass_config.style.display = "none";
		changeHeight.style.height = "20px";
		return;
	}

	// Show config
	pass_config.style.display = "block";
	changeHeight.style.height = "70px";

	// Check each rule
	if (/[a-z]/.test(fieldData)) {
		lower.style.color = "green";
		hasLower=true;
	}
	if (/[A-Z]/.test(fieldData)) {
		upper.style.color = "green";
		hasUpper=true;
	}
	if (/[0-9]/.test(fieldData)) {
		num.style.color = "green";
		hasNumber=true;
	}
	if (/[^A-Za-z0-9]/.test(fieldData)) {
		spacial.style.color = "green";
		hasSpecial=true;
	}
	if (fieldData.length >= 8) {
		character.style.color = "green";
		hasLength=true;
	}
});
function isPasswordValid() {
	return hasLower && hasUpper && hasNumber && hasSpecial && hasLength;
}
const confirmPassword = document.getElementById("confirm_password");


confirmPassword.addEventListener("focus", function () {
	if (!isPasswordValid()) {
		this.blur(); // Prevent typing
		pass_error.innerText = "Complete password requirements first.";
	}
	if(isPasswordValid()){
		pass_config.style.display = "none";
		changeHeight.style.height = "20px";
	}
	
});

//password validation ending here;




//herer main work is done  sending data to the servlet for store
document.addEventListener("DOMContentLoaded", function () {
    
	document.getElementById('check').style.display='none';
    const registerButton = document.getElementById("btn");

    if (!registerButton) {
        console.error("Register button not found!");
        return;
    }

    registerButton.addEventListener("click", async function (event) {
        event.preventDefault();
		//initialli all error field is empty
		clearErrors()
						
						
		const currentEmail = emailInput.value.trim();

		
			

        const fname = document.getElementById("fname")?.value.trim();
        const lname = document.getElementById("lname")?.value.trim();
        const email = document.getElementById("email")?.value.trim();
        const phone = document.getElementById("phone")?.value.trim();
        const password = document.getElementById("password")?.value.trim();
        const role = document.getElementById("role")?.value.trim();
        const dept = document.getElementById("dept")?.value.trim();
		const confirmPassword = document.getElementById("confirm_password")?.value.trim();
		
        if (!fname || !lname || !email || !phone || !password || !role || !dept) {
            
            alert("Please fill in all fields.");
            return;
        }
		
		
		
		
		
		
			
		if(password !== confirmPassword){
			pass_error.innerText="password don't match";
			document.getElementById('confirm_password').focus();
			document.getElementById('confirm_password').style.borderColor="red";
			return;
		}
		

		
		if (verifiedEmail !== currentEmail) {
			        event.preventDefault();
					clearErrors();
			        email_error.innerText="Verify your email before submitting.";
					return;
			    }
		
        const formData = { fname, lname, email, phone, pass: password, role, dept };

        try {
            let response = await fetch("http://localhost:8080/complaint-management/registerUser", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                throw new Error(`Server responded with status ${response.status}`);
            }

            let data = await response.json();
            if(data.message ==="success"){
				window.location.href="login.html";
				return;
			}
			else{
				alert(data.message);
			}
            
        } catch (error) {
            console.error("Error:", error);
            alert("Failed to submit form. Please try again.");
        }
    });
});















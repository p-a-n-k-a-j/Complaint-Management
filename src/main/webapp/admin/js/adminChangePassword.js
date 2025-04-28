document.addEventListener('DOMContentLoaded', function () {
  console.log("DOM loaded");

  const oldPasswordField = document.getElementById('oldPassword');
  const newPasswordField = document.getElementById('newPassword');
  const confirmPasswordField = document.getElementById('confirmPass');
  const errorMessage = document.getElementById('errorMessage');

  // 👁️ Toggle password visibility
  document.getElementById('show-password').addEventListener('change', function () {
    const type = this.checked ? 'text' : 'password';
    oldPasswordField.type = type;
    newPasswordField.type = type;
    confirmPasswordField.type = type;
  });

  // 🔐 Password update logic
  document.getElementById('btn').addEventListener('click', async function (e) {
    e.preventDefault();

    const oldPassword = oldPasswordField.value;
    const newPassword = newPasswordField.value;
    const confirmPassword = confirmPasswordField.value;

    // 🔍 Check if all fields are filled
    if (!oldPassword || !newPassword || !confirmPassword) {
      errorMessage.innerText = "Please fill all fields";
      errorMessage.classList.add('show');
      return;
    }

    // ❌ Passwords must match
    if (newPassword !== confirmPassword) {
      errorMessage.innerText = "Passwords don't match";
      errorMessage.classList.add('show');
      return;
    }

    // ✅ Clear error message
    errorMessage.innerText = "";
    errorMessage.classList.remove('show');

  
    const formData = { oldPassword, newPassword };

    try {
      console.log('Updating password...');
      const response = await fetch("http://localhost:8080/complaint-management//UpdateAdminPassword", {
        method: 'POST',
        headers: {
          'Content-Type': "application/json"
        },
        body: JSON.stringify(formData)
      });

      if (!response.ok) {
        throw new Error("Network error");
      }

      const data = await response.json();

      if (data.message === "success") {
        alert("✅ Password updated successfully!");
        oldPasswordField.value = "";
        newPasswordField.value = "";
        confirmPasswordField.value = "";
      } else {
        alert("❌ Failed to update password. Please check your old password.");
      }

    } catch (error) {
      console.error("Error:", error);
      alert("⚠️ Server error. Please try again later.");
    }
  });
});
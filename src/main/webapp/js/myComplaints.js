document.addEventListener("DOMContentLoaded", async function () {

  console.log("i am running");

  try {
    let response = await fetch('http://localhost:8080/complaint-management/MyComplaints', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    let data = await response.json();

    const tbody = document.getElementById('complaint-data');
    tbody.innerHTML = ""; // Clear old rows

    if (Array.isArray(data) && data.length > 0) {
      // Render complaints
      data.forEach(item => {
		const statusClass = `status-${item.status.toLowerCase()}`;
        const row = `
          <tr>
            <td>${item.title}</td>
            <td><span class="${statusClass}">${item.status}</span></td>
            <td>${item.updatedAt}</td>
            <td>${item.updatedBy}</td>
            <td>${item.priority}</td>
          </tr>
        `;
        tbody.insertAdjacentHTML('beforeend', row);
      });
    } else {
      // No complaints found
      tbody.innerHTML = `
        <tr>
          <td colspan="5" style="text-align:center; font-weight: bold; color: red;">
            No complaints found
          </td>
        </tr>
      `;
    }

  } catch (error) {
    console.error("Fetch error:", error);
  }
});

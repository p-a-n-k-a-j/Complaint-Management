document.addEventListener("DOMContentLoaded", async function () {

  try {
    let response = await fetch('http://localhost:8080/complaint-management/ComplaintStatus', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    let data = await response.json();
console.log(data)
    // Check if the response contains valid data
    if (data && Array.isArray(data) && data.length > 0) {
      // ====== Render complaint status ======
      const tbody = document.getElementById('complaint-data');
      tbody.innerHTML = ""; // Clear old rows if any

      // Iterate through the complaint status data and render each item
      data.forEach(item => {
		
		const statusClass = `status-${item.status.toLowerCase()}`;
        const row = `
          <tr>
            <td>${item.title}</td>
            <td><span class="${statusClass}">${item.status}</span></td>
            <td>${item.updatedAt}</td>
            <td>${item.updatedBy}</td>
            <td>${item.remark}</td>
          </tr>
        `;
        tbody.insertAdjacentHTML('beforeend', row);
      });
    } else {
      // If no data is available, display the no data message
      const tbody = document.getElementById('complaint-data');
      tbody.innerHTML = `
        <tr>
          <td colspan="5" style="text-align:center; font-weight: bold; color: red;">No complaint status found</td>
        </tr>
      `;
    }

  } catch (error) {
    console.error("Fetch error:", error);
  }
});

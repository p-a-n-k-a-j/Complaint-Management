async function loadDashboardData() {
  try {
    let response = await fetch('http://localhost:8080/complaint-management/recentData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error("HTTP error " + response.status);
    }

    let data = await response.json();
    console.log(data);

    const tbody = document.getElementById('recent-data');

    // ====== Render recent log ======
    if (data && Array.isArray(data.recentLog) && data.recentLog.length > 0) {
      tbody.innerHTML = ""; // Clear old rows
      data.recentLog.forEach(item => {
		
		
        const statusValue = item.status || "pending";
        const statusClass = `status-${statusValue.toLowerCase()}`;
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
      tbody.innerHTML = `
        <tr>
          <td colspan="5" style="text-align:center; font-weight: bold; color: red;">No recent complaints found</td>
        </tr>
      `;
    }

    // ====== Render status cards ======
    document.querySelectorAll(".card").forEach(card => {
      const key = card.getAttribute("data-status");
      const num = card.querySelector(".card-number");
		if(data.status.username){
			document.getElementById('username').innerText=data.status.username;
		}
      if (data.status && data.status[key] !== undefined && num) {
		
		    num.innerText = data.status[key];
		

      } else {
        console.warn(`No data found for status: ${key}`);
      }
    });

  } catch (error) {
    console.error("Fetch error:", error);
  }
}

window.onload = () => {
  loadDashboardData(); // Initial load
  setInterval(loadDashboardData, 10000); // Refresh every 10 seconds
};

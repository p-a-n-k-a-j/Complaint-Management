let data = []; // Store all complaints data
const tableContainer = document.getElementById('tableContainer');
const searchInput =document.getElementById('searchInput');
document.addEventListener("DOMContentLoaded", async function () {

  
  try {
    let response = await fetch('http://localhost:8080/complaint-management/MyComplaints', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }
    });

    if (!response.ok) throw new Error('Failed to fetch data from the server');
    
    data = await response.json(); // Save all fetched data

    
    
  } catch (error) {
    console.error("Fetch error:", error);
  }
});

// Function to render complaints into the table
function renderComplaints(data) {
  const tbody = document.getElementById('complaint-data');
  tbody.innerHTML = ''; // Clear previous complaints

  if (data.length === 0) {
    const row = `
      <tr>
        <td colspan="5" style="text-align: center;">No complaints found</td>
      </tr>
    `;
    tbody.insertAdjacentHTML('beforeend', row);
  } else {
    data.forEach(item => {
      // Normalize status to lowercase
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
  }
}


// Add event listener for the search input
searchInput.addEventListener("input", function () {

  const query = this.value.toLowerCase(); // Get the search query
  window.setTimeout(() => { 
  if (query === "") {
      tableContainer.style.display = "none";
    } else {
      tableContainer.style.display = "block";
    }
  
  const filteredData = data.filter(item => 
    item.title.toLowerCase().includes(query) || 
    item.status.toLowerCase().includes(query) || 
    item.updatedBy.toLowerCase().includes(query)||
	item.priority.toLowerCase().includes(query)
  );
  
    renderComplaints(filteredData);
  }, 50); // 50 seconds

});

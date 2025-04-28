window.onload = async function () {

  try {
    let response = await fetch('http://localhost:8080/complaint-management/RecentComplaint', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error("HTTP error " + response.status);
    }

    let data = await response.json();
   
	
    const tbody = document.getElementById('recent-data');

    // ====== Render recent log ======
	if (
	  data &&
	  Array.isArray(data.Complaints) &&
	  data.Complaints.length > 0
	) {
	  tbody.innerHTML = ""; // Clear old rows if any

	  // 🧠 Fixing here: looping through Complaints (not recentLog)
	  data.Complaints.forEach(item => {
		
		const statusValue = item.status || "pending";
	    const statusClass = `status-${statusValue.toLowerCase()}`;
		
	    const row = `
	      <tr>
	        <td>${item.username || '-'}</td>
	        <td>${item.title}</td>
	        <td><span class="${statusClass}">${item.status}</span></td>
	        <td>${item.createdAt || '-'}</td>
	        <td>${item.updatedAt || '-'}</td>
	        <td>${item.updatedBy || '-'}</td>
	        <td><button class="view"  data="${item.id}">View</button></td>
	      </tr>
	    `;
	    tbody.insertAdjacentHTML('beforeend', row);
		// After all rows have been added
		document.querySelectorAll('.view').forEach(button => {
		  button.addEventListener('click', async function () {
		    const userDetails = document.getElementById('userDetails');
		    userDetails.style.display = "block";

		    const complaintId = this.getAttribute('data'); // fetch the complaint ID
		    
			
			
			//here i need to get username title, status , priority, createdAt, updatedat, updateBy,description
			try {	
				let response = await fetch('http://localhost:8080/complaint-management/ViewComplaintDetails', {
					method: 'POST',
					headers: { 'Content-Type': "application/json" },
					body: JSON.stringify({ id: complaintId })
				});
				
				let data = await response.json();

				if (data && Object.keys(data).length > 0) {
					

					// Set values to HTML spans
					document.getElementById("user-username").innerText = data.username || "-";
					document.getElementById("title").innerText = data.title || "-";
					document.getElementById("status").innerText = data.status || "-";
					document.getElementById("priority").innerText = data.priority || "-";
					document.getElementById("createdAt").innerText = data.createdAt || "-";
					document.getElementById("updatedAt").innerText = data.updatedAt || "-";
					document.getElementById("updatedBy").innerText = data.updatedBy || "-";
					document.getElementById("description").innerText = data.description || "-";

				} else {
					alert("Data not received");
				}
			} catch (error) {
				alert("Server error: " + error.message);
				console.error('Fetch error:', error);
			}

		  });
		});

	  });
	}
	else {
      tbody.innerHTML = `
        <tr>
          <td colspan="8" style="text-align:center; font-weight: bold; color: red;">No recent complaints found</td>
        </tr>
      `;
    }

    // ====== Render status cards ======
    document.querySelectorAll(".card").forEach(card => {
      const key = card.getAttribute("data-status"); // 'totalComplaint', 'pending', etc.
      const num = card.querySelector(".card-number");
	  
	  if(data.status.username){
	  	  			
	  	  		    document.getElementById('AdminUsername').innerText = data.status.username;
	  	  }
      if (data.status && data.status[key] !== undefined && num) {
	
		    num.innerText = data.status[key];
		

         // Update the card number with the data
      } else {
        console.warn(`No data found for status: ${key}`);
      }
    });

  } catch (error) {
    console.error("Fetch error:", error);
    alert("Failed to load recent data. Try again later.");
  }
};


document.addEventListener('DOMContentLoaded', function(){


	//for close button:
	document.getElementById('close').addEventListener('click', async function(){
		const userDetails = document.getElementById('userDetails');
		userDetails.style.display="none";
	})
})



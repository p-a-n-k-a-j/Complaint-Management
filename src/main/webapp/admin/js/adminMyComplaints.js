document.addEventListener("DOMContentLoaded", function () {
  const searchInput = document.getElementById("searchInput");
  const tableContainer = document.getElementById("tableContainer");
  const tbody = document.getElementById("complaint-data");

  // Load all complaints initially
  loadComplaint();

  // Search filter event
  if (searchInput) {
    searchInput.addEventListener("input", async function () {
      const query = this.value.toLowerCase();

      if (query === "") {
        await loadComplaint(); // Load all if empty
      } else {
        try {
          const response = await fetch("http://localhost:8080/complaint-management/UserComplaints", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
          });

          const data = await response.json();

          const filteredData = data.filter((item) =>
            item.title.toLowerCase().includes(query) ||
            item.status.toLowerCase().includes(query) ||
            item.updatedBy.toLowerCase().includes(query) ||
            item.priority.toLowerCase().includes(query)
          );

          renderComplaints(filteredData);
        } catch (error) {
          console.error("Search fetch error:", error);
        }
      }
    });
  }

  // Function to load complaints
  async function loadComplaint() {
    try {
      const response = await fetch("http://localhost:8080/complaint-management/UserComplaints", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });

      const data = await response.json();
      renderComplaints(data);
    } catch (error) {
      console.error("Error loading complaints:", error);
    }
  }

  // Render complaints in the table
  function renderComplaints(data) {
    tbody.innerHTML = "";

    if (data.length === 0) {
      tbody.innerHTML = `
        <tr>
          <td colspan="5" style="text-align:center; font-weight: bold; color: red;">
            No complaints found
          </td>
        </tr>
      `;
      return;
    }

    data.forEach((item) => {
      const statusValue = item.status || "Pending";
      const statusClass = `status-${statusValue.toLowerCase()}`;

      const row = `
        <tr data-id="${item.id}">
          <td>${item.title}</td>
          <td>
            <select class="status-dropdown ${statusClass}">
              <option value="Pending" ${statusValue === "Pending" ? "selected" : ""}>Pending</option>
              <option value="InProgress" ${statusValue === "InProgress" ? "selected" : ""}>In Progress</option>
              <option value="Resolved" ${statusValue === "Resolved" ? "selected" : ""}>Resolved</option>
            </select>
          </td>
          <td>${item.updatedAt}</td>
          <td>${item.updatedBy}</td>
          <td>${item.priority}</td>
        </tr>
      `;

      tbody.insertAdjacentHTML("beforeend", row);
    });

    // 🔥 Attach status update listener
    document.querySelectorAll(".status-dropdown").forEach((select) => {
      select.addEventListener("change", async function () {
        const newStatus = this.value;
        const row = this.closest("tr");
        const complaintId = row.getAttribute("data-id");

        this.disabled = true;

        try {
          const response = await fetch("http://localhost:8080/complaint-management/UpdateStatus", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              id: complaintId,
              status: newStatus,
            }),
          });

          const result = await response.json();

          if (result.success) {
            alert("Status updated successfully!");
            await loadComplaint(); // refresh
          } else {
            alert("Failed to update status.");
          }
        } catch (error) {
          console.error("Error updating status:", error);
          alert("Something went wrong.");
        } finally {
          this.disabled = false;
        }
      });
    });
  }
});

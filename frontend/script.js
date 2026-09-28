const API_URL = "http://localhost:8080/api";


// ===============================
// Load complaints
// ===============================

async function loadComplaints() {

    try {

        const response = await fetch(`${API_URL}/complaints`);

        if (!response.ok) {
            throw new Error("Failed to load complaints");
        }

        const complaints = await response.json();

        displayComplaints(complaints);

        updateDashboard(complaints);

    } catch (error) {

        console.error(error);

        document.getElementById("complaintTableBody").innerHTML =
            `<tr>
                <td colspan="9">
                    Unable to connect to Spring Boot server.
                </td>
            </tr>`;
    }
}


// ===============================
// Display complaints
// ===============================

function displayComplaints(complaints) {

    const tableBody =
        document.getElementById("complaintTableBody");

    tableBody.innerHTML = "";

    if (complaints.length === 0) {

        tableBody.innerHTML =
            `<tr>
                <td colspan="9">
                    No complaints found.
                </td>
            </tr>`;

        return;
    }


    complaints.forEach(complaint => {

        let statusClass = "";

        if (complaint.status === "OPEN") {
            statusClass = "status-open";
        }

        if (complaint.status === "IN_PROGRESS") {
            statusClass = "status-progress";
        }

        if (complaint.status === "RESOLVED") {
            statusClass = "status-resolved";
        }


        const row = document.createElement("tr");

        row.innerHTML = `

            <td>${complaint.id}</td>

            <td>${complaint.roomNumber}</td>

            <td>${complaint.description}</td>

            <td>${complaint.categoryId}</td>

            <td>${complaint.residentId}</td>

            <td>
                ${complaint.staffId ?? "Not Assigned"}
            </td>

            <td class="${statusClass}">
                ${complaint.status}
            </td>

            <td class="${complaint.overdue ? "overdue" : ""}">
                ${complaint.overdue ? "YES" : "NO"}
            </td>

            <td>

                ${
            complaint.status === "OPEN"
                ?
                `<button onclick="assignStaff(${complaint.id})">
                        Assign Staff
                    </button>`
                :
                ""
        }

                ${
            complaint.status === "OPEN"
                ?
                `<button onclick="changeStatus(${complaint.id}, 'IN_PROGRESS', ${complaint.staffId || 1})">
                        Start
                    </button>`
                :
                ""
        }

                ${
            complaint.status === "IN_PROGRESS"
                ?
                `<button onclick="changeStatus(${complaint.id}, 'RESOLVED', ${complaint.staffId})">
                        Resolve
                    </button>`
                :
                ""
        }

            </td>
        `;

        tableBody.appendChild(row);

    });
}


// ===============================
// Dashboard
// ===============================

function updateDashboard(complaints) {

    const total = complaints.length;

    const open = complaints.filter(
        complaint => complaint.status === "OPEN"
    ).length;

    const progress = complaints.filter(
        complaint => complaint.status === "IN_PROGRESS"
    ).length;

    const resolved = complaints.filter(
        complaint => complaint.status === "RESOLVED"
    ).length;


    document.getElementById("totalComplaints").textContent = total;

    document.getElementById("openComplaints").textContent = open;

    document.getElementById("progressComplaints").textContent = progress;

    document.getElementById("resolvedComplaints").textContent = resolved;
}


// ===============================
// Create complaint
// ===============================

document.getElementById("complaintForm").addEventListener(
    "submit",
    async function(event) {

        event.preventDefault();


        const residentId =
            document.getElementById("residentId").value;

        const categoryId =
            document.getElementById("categoryId").value;

        const roomNumber =
            document.getElementById("roomNumber").value;

        const description =
            document.getElementById("description").value;


        const complaint = {

            residentId: Number(residentId),

            categoryId: Number(categoryId),

            roomNumber: roomNumber,

            description: description
        };


        try {

            const response = await fetch(
                `${API_URL}/complaints`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(complaint)
                }
            );


            if (!response.ok) {
                throw new Error("Failed to create complaint");
            }


            document.getElementById("message").textContent =
                "Complaint submitted successfully!";


            document.getElementById("complaintForm").reset();


            loadComplaints();


        } catch (error) {

            console.error(error);

            document.getElementById("message").textContent =
                "Failed to submit complaint.";

        }

    }
);


// ===============================
// Assign staff
// ===============================

async function assignStaff(complaintId) {

    const staffId = prompt(
        "Enter Staff ID:"
    );


    if (!staffId) {
        return;
    }


    try {

        const response = await fetch(
            `${API_URL}/complaints/${complaintId}/assign/${staffId}`,
            {
                method: "PUT"
            }
        );


        if (!response.ok) {
            throw new Error("Failed to assign staff");
        }


        alert("Staff assigned successfully!");

        loadComplaints();


    } catch (error) {

        console.error(error);

        alert("Failed to assign staff.");

    }
}


// ===============================
// Change status
// ===============================

async function changeStatus(
    complaintId,
    status,
    staffId
) {

    if (!staffId) {

        alert("Please assign a staff member first.");

        return;
    }


    try {

        const response = await fetch(
            `${API_URL}/complaints/${complaintId}/status?status=${status}&staffId=${staffId}`,
            {
                method: "PUT"
            }
        );


        if (!response.ok) {
            throw new Error("Status update failed");
        }


        alert(
            `Complaint status changed to ${status}`
        );


        loadComplaints();


    } catch (error) {

        console.error(error);

        alert(
            "Status update failed. Check the complaint workflow."
        );

    }
}


// ===============================
// Load complaints when page opens
// ===============================

loadComplaints();
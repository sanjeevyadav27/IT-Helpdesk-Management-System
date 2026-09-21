// =========================
// LOGIN
// =========================

const loginForm =
    document.getElementById("loginForm");

const ticketForm =
    document.getElementById("ticketForm");


loginForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();

        const email =
            document.getElementById("email").value;

        const password =
            document.getElementById("password").value;

        const message =
            document.getElementById("loginMessage");


        if (email === "" || password === "") {

            message.textContent =
                "Please enter email and password.";

            return;
        }


        try {

            const response = await fetch(

                "http://localhost:8080/api/login?" +
                "email=" +
                encodeURIComponent(email) +
                "&password=" +
                encodeURIComponent(password)

            );

            const result =
                await response.text();

            message.textContent =
                result;

        } catch (error) {

            message.textContent =
                "Unable to connect to backend.";

            console.error(error);
        }

    }
);


// =========================
// CREATE TICKET
// =========================

ticketForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();

        const title =
            document.getElementById("title").value;

        const description =
            document.getElementById("description").value;

        const priority =
            document.getElementById("priority").value;

        const categoryId =
            document.getElementById("category").value;

        const message =
            document.getElementById("ticketMessage");


        if (title === "" || description === "") {

            message.textContent =
                "Please fill all required fields.";

            return;
        }


        try {

            const response = await fetch(

                "http://localhost:8080/api/tickets?" +

                "title=" +
                encodeURIComponent(title) +

                "&description=" +
                encodeURIComponent(description) +

                "&priority=" +
                encodeURIComponent(priority) +

                "&categoryId=" +
                encodeURIComponent(categoryId)

            );

            const result =
                await response.text();

            message.textContent =
                result;


            if (
                result ===
                "Ticket Created Successfully!"
            ) {

                ticketForm.reset();

            }

        } catch (error) {

            message.textContent =
                "Unable to connect to backend.";

            console.error(error);
        }

    }
);


// =========================
// VIEW MY TICKETS
// =========================

const viewTicketsBtn =
    document.getElementById(
        "viewTicketsBtn"
    );

const ticketsList =
    document.getElementById(
        "ticketsList"
    );


viewTicketsBtn.addEventListener(
    "click",
    async function () {

        ticketsList.innerHTML =
            "<p>Loading tickets...</p>";


        try {

            const response =
                await fetch(
                    "http://localhost:8080/api/my-tickets"
                );

            const result =
                await response.text();


            if (result.trim() === "") {

                ticketsList.innerHTML =
                    "<p>No tickets found.</p>";

            } else {

                ticketsList.innerHTML =
                    "<pre>" +
                    result +
                    "</pre>";

            }

        } catch (error) {

            ticketsList.innerHTML =
                "<p>Unable to connect to backend.</p>";

            console.error(error);
        }

    }
);


// =========================
// ADMIN - VIEW ALL TICKETS
// =========================

const viewAllTicketsBtn =
    document.getElementById(
        "viewAllTicketsBtn"
    );

const allTicketsList =
    document.getElementById(
        "allTicketsList"
    );


viewAllTicketsBtn.addEventListener(
    "click",
    async function () {

        allTicketsList.innerHTML =
            "<p>Loading all tickets...</p>";


        try {

            const response =
                await fetch(
                    "http://localhost:8080/api/all-tickets"
                );

            const result =
                await response.text();


            if (result.trim() === "") {

                allTicketsList.innerHTML =
                    "<p>No tickets found.</p>";

            } else {

                allTicketsList.innerHTML =
                    "<pre>" +
                    result +
                    "</pre>";

            }

        } catch (error) {

            allTicketsList.innerHTML =
                "<p>Unable to connect to backend.</p>";

            console.error(error);
        }

    }
);


// =========================
// ADMIN - ASSIGN TICKET
// =========================

const assignTicketForm =
    document.getElementById(
        "assignTicketForm"
    );

const assignMessage =
    document.getElementById(
        "assignMessage"
    );


assignTicketForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const ticketId =
            document.getElementById(
                "assignTicketId"
            ).value;


        const agentId =
            document.getElementById(
                "assignAgentId"
            ).value;


        if (
            ticketId === "" ||
            agentId === ""
        ) {

            assignMessage.textContent =
                "Please enter Ticket ID and Agent ID.";

            return;
        }


        assignMessage.textContent =
            "Assigning ticket...";


        try {

            const response = await fetch(

                "http://localhost:8080/api/assign-ticket?" +

                "ticketId=" +
                encodeURIComponent(ticketId) +

                "&agentId=" +
                encodeURIComponent(agentId)

            );


            const result =
                await response.text();


            assignMessage.textContent =
                result;


            if (
                result ===
                "Ticket Assigned Successfully!"
            ) {

                assignTicketForm.reset();

                viewAllTicketsBtn.click();

            }

        } catch (error) {

            assignMessage.textContent =
                "Unable to connect to backend.";

            console.error(error);
        }

    }
);


// =========================
// AGENT - VIEW ASSIGNED TICKETS
// =========================

const viewAssignedTicketsBtn =
    document.getElementById(
        "viewAssignedTicketsBtn"
    );


const assignedTicketsList =
    document.getElementById(
        "assignedTicketsList"
    );


viewAssignedTicketsBtn.addEventListener(
    "click",
    async function () {

        const agentId =
            document.getElementById(
                "agentDashboardId"
            ).value;


        if (agentId === "") {

            assignedTicketsList.innerHTML =
                "<p>Please enter Agent ID.</p>";

            return;
        }


        assignedTicketsList.innerHTML =
            "<p>Loading assigned tickets...</p>";


        try {

            const response =
                await fetch(

                    "http://localhost:8080/api/assigned-tickets?" +
                    "agentId=" +
                    encodeURIComponent(agentId)

                );


            const result =
                await response.text();


            if (result.trim() === "") {

                assignedTicketsList.innerHTML =
                    "<p>No assigned tickets found.</p>";

            } else {

                assignedTicketsList.innerHTML =
                    "<pre>" +
                    result +
                    "</pre>";

            }

        } catch (error) {

            assignedTicketsList.innerHTML =
                "<p>Unable to connect to backend.</p>";

            console.error(error);
        }

    }
);


// =========================
// AGENT - UPDATE STATUS
// =========================

const updateStatusForm =
    document.getElementById(
        "updateStatusForm"
    );


const statusMessage =
    document.getElementById(
        "statusMessage"
    );


updateStatusForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const ticketId =
            document.getElementById(
                "statusTicketId"
            ).value;


        const status =
            document.getElementById(
                "ticketStatus"
            ).value;


        if (ticketId === "") {

            statusMessage.textContent =
                "Please enter Ticket ID.";

            return;
        }


        statusMessage.textContent =
            "Updating ticket status...";


        try {

            const response = await fetch(

                "http://localhost:8080/api/update-status?" +

                "ticketId=" +
                encodeURIComponent(ticketId) +

                "&status=" +
                encodeURIComponent(status)

            );


            const result =
                await response.text();


            statusMessage.textContent =
                result;


            if (
                result ===
                "Ticket Status Updated Successfully!"
            ) {

                updateStatusForm.reset();

            }

        } catch (error) {

            statusMessage.textContent =
                "Unable to connect to backend.";

            console.error(error);
        }

    }
);
# IT Helpdesk Management System

A role-based IT Helpdesk Management System developed using Java, JDBC, MySQL, HTML, CSS, and JavaScript.

The system allows employees to create and track support tickets, support agents to manage assigned tickets, and administrators to assign and monitor tickets.

## Features

### Employee
- User login
- Create IT support tickets
- Select ticket priority
- Select ticket category
- View submitted tickets
- Track ticket status

### Support Agent
- View assigned tickets
- View ticket priority and details
- Update ticket status
- Resolve support tickets

### Admin
- Admin login
- View all support tickets
- View ticket creator and assigned agent
- Assign tickets to support agents
- Monitor ticket status

## Ticket Workflow

```text
Employee
   |
   | Create Ticket
   v
OPEN
   |
   | Admin assigns Agent
   v
IN_PROGRESS
   |
   | Agent resolves issue
   v
RESOLVED
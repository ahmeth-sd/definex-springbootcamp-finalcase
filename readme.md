Problem Definition
Subject: Advanced Task Management

Story: Lorem Ipsum corporation has decided to change its legacy task manager application. The current application become outdated, slow and does not meet the current requirements of the corporation anymore. After a serious alternative tool research, the company board decided to develop an Advanced Task Management application in-house. Your task as a Developer is to implement this application. In the next sections you can find some details about the application.

Major Features:
Project and task management: As project group manager, I shall be able to manage projects related to a department. Also I shall be able to manage tasks of a project.

Team member assignment: As a team leader or project manager(in a deparment), I shall be able to assign team members to a task.

Progress tracking: As a team leader or project manager, I should be able to track the progress of a task. The progress shall have states (Backlog, In Analysis, In Development/Progress, Cancelled, Blocked or Completed).

Priority management: As a team leader or project manager, I should be able to assign priorities with task as critical, high, medium and low.

File attachment support: As a team member (Project Manager, Team Leader or other Team Members), I shall be able to attach one or more files to a task. The files shall be stored on the disk.

Constraints
While implementing state management task progression:

Happy Path: Backlog <=> In Analysis <=> In Development/Progress <=> Completed

Cancel Path: In any state except Completed , the progress state can be assigned as Cancelled .

Blocked Paths:

In Analysis ⇔ Blocked

In Development/Progress <=> Blocked

When the state of a Task is assigned as Cancelled or Blocked , a reason must be entered.

Under no circumstances, a Task, which state is Completed , CANNOT be putback to any state.

A team member (except Team Leader or Project Manager):

shall NOT be able to change the description and title of a task.

shall be able to change the attachments or state of the task.

shall be able to add comments.

At minimum a task shall be able provide information about:

A user story description.

An acceptance criteria.

A state

A priority

A list of comments

A list of attachments

An assignee

At minimum, a Project shall be able provide information about:

Responsible Department's Name

A status (In Progress, Cancelled or Completed)

A title

A description

A list of team members working on the project

All DELETE operations are passive meaning records cannot be deleted from the storage.

Unit tests is expected to be implemented.

According to the corporation's quality standard, code coverage shall meet %80 of the code.

The backend of the application shall be implemented with Java 21 and Spring Boot 3.

Not: Further information about frontend part will be announced later.

Good Luck!


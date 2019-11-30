>  This is a repository of the comp2100/6442 group project. We made an Android time-tabling assistant for ANU students.

# App: Pocket Timetable

## Team structure and roles

* <b>Yuqing Zhai (u6865190)</b>: enrol page code, tricky android code, graphics rendering
* <b>Yongchao Lyu (u6874539)</b>: web crawler, main page code, tricky android code, internal storage operation
* <b>Jingwei Wang (u6891978)</b>: interface design and logic implementation for connecting front-end and back-end
* <b>Xiaochan Zhang (u6855326)</b>: add page code, tricky android code, graphics rendering, note-taker

## App Overview

### Home Page
- Display current courses you have enrolled in a weekly calendar.
- Find detailed course information by clicking the course block. 
- If you click the DETAILS button inside the block, you will go directly to the corresponding ANU official course website.

![](course_detail.gif)

### Enrol Page
- Display all courses that you have enrolled.
- Delete any courses you have enrolled by selecting the course and clicking the delete button.
- Display all recommendation courses for ANU COMP students based on computing specializations.
- Search any courses in ANU database using ANU course code.
- Select corresponding tutorials, and click the enrol button. If there are any conflicts, you will get a red message. Otherwise, you will automatically return to the home page.

![](delete.gif)

![](enrol.gif)

### Add Page
- Modify any courses and tutorials in ANU database.
- Add custom courses and tutorials.

![](add.gif)

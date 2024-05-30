

# **DOCUMENTATION**
## **Table of Contents 1.2.5**
[Colors](#COLORS)



## **COLORS**

**Purpose:**

This composable provides a user interface for customizing the color scheme of an Android application built with 
Jetpack Compose. It includes input fields for setting primary, secondary, tertiary, and text colors, with mechanisms 
to ensure valid color input, save changes, and provide user feedback.

**Key Components and Functionality:**

1. **Data Class `ColorScheme`:**
    - Represents a set of four colors as hexadecimal strings.

2. **`parseColor` Function:**
    - Converts a hexadecimal color string to a Jetpack Compose `Color` object.
    - Handles invalid input by returning `Color.Unspecified`.

3. **`globalcolors` Object:**
    - Manages the application's color scheme.
    - `COLORS_FILE_NAME`:  Stores the filename for saving color settings.
    - `defaultScheme`:  Provides default colors if no saved scheme is found.
    - `currentScheme`:  Holds the currently active color scheme as a state variable.
    - `loadColorScheme`:  Loads the color scheme from a JSON file in the app's internal storage.
    - `saveColorScheme`:  Saves the given color scheme to the JSON file, deleting any previous version to ensure consistency.
    - `resetToDefaultColors`: Resets the colors to their default values.
    - `primaryColor`, `secondaryColor`, `tertiaryColor`, `textColor`: Derived properties that return the respective `Color` objects from the `currentScheme`.

4. **`ColorSettings` Composable Function:**
    - **State Management:**
        - Manages local state variables for each color (`primaryColor`, `secondaryColor`, etc.)
        - Uses `LaunchedEffect` to keep these variables in sync with `globalcolors.currentScheme`.
    - **Input Fields (`OutlinedColorTextField`):**
        - Four text fields for inputting hex color codes.
        - Includes an error checking mechanism (`isValidHexColor`) to validate the input.
        - The `onValueChange` callback updates the corresponding local state variable without modifying the `globalcolors` immediately.
    - **Save and Revert Buttons:**
        - The "Save Colors" button:
            - Creates a new `ColorScheme` object from the input values.
            - Calls `globalcolors.saveColorScheme` to persist the changes.
            - Triggers the `onSave` lambda (passed from the parent composable), which likely refreshes the app's UI to reflect the new colors.
            - After saving, displays an alert dialog message about the upcoming refresh.
        - The "Revert to Default Colors" button:
            - Calls `globalcolors.resetToDefaultColors` to restore default colors.
            - Triggers the `onRevert` lambda for UI refresh.
            - Also displays the refresh alert dialog message.

5. **`OutlinedColorTextField` Composable Function:**
    - A reusable composable for displaying an outlined text field for color input.
    - Uses `isValidHexColor` to validate input.
    - Displays an error state and message if the color code is invalid.
    - Colors of the text field elements (border, cursor, etc.) are derived from the `globalcolors` scheme.

**Error Handling and UI Refresh:**

- **Color Code Validation:**
    - The `isValidHexColor` function checks if the input string is a valid six-digit hexadecimal color code (e.g., "FF0000" for red).
    - If the input is invalid, an error message is displayed, and the color is not updated.
- **Delayed Color Application and Refresh:**
    - Colors are not applied directly on input change. Instead, they are saved and applied only when the "Save Colors" button is pressed.
    - After saving or reverting, the `onSave` or `onRevert` lambdas are called, likely triggering navigation or recomposition to refresh the UI and display the new colors.

**Key Improvements:**
- **User Experience:** The delayed color application and refresh dialog message provide a clear indication to the user that the changes will take effect after a screen refresh.
- **Robustness:** Error checking prevents the app from crashing or displaying incorrect colors if invalid input is entered.
- **Maintainability:** The code structure is organized and reusable, with clear separation of concerns between color input, validation, and saving/loading the color scheme.





Absolutely! Let's break down the provided code and provide a detailed documentation of its functionality, components, and potential improvements:

**AddStudentScreen Composable**

This composable function is designed to handle the addition of new students to a class roster or database. Let's analyze its key aspects:

1. **Structure and Layout:**
    - Uses a `Scaffold` to provide a basic structure with a top app bar.
    - The main content is a `Column` arranged vertically to hold various elements:
        - Instructions for the user
        - Text fields for collecting student information
        - A button to trigger the "Add Student" action
        - A custom function (`TextFields`) is used to create reusable input fields.

2. **Input Fields:**
    - It collects the student's first name, last name, and ID using `TextField` composables.
    - `TextFields` function encapsulates the common styling for these input fields, promoting consistency.
    - It validates the student ID against a specific pattern (`Regex`).
    - Input is sanitized by trimming extra spaces on the right using `.trimEnd()`.

3. **Functionality:**
    - When the "Add Student" button is clicked:
        - It checks if all required fields are filled and if the student ID matches the pattern.
        - If the input is valid, it:
            - Loads the existing student data using `FileUtil.loadStudents()`.
            - Adds the new student to the list.
            - Saves the updated list using `FileUtil.saveStudents()`.
            - Clears the input fields.
            - Displays a success message using a Toast.
            - Calls the `onStudentAdded` lambda (presumably to update the UI).
        - If the student ID is invalid, it displays an error message.

4. **Styling:**
    - Uses a gradient background (`addbackbrush`) for visual appeal.
    - Text colors, font sizes, and shapes are managed using the `globalcolors` object for a consistent theme.


**TextFields Composable**

This is a reusable composable function to create input fields with a consistent style:

- Takes parameters for the current value, a callback (`onValueChange`) to update the value, and an optional label.
- Applies consistent styling (font, colors, shape) to the `TextField` composable.


**Potential Improvements**

Here are some suggestions to improve the code:

- **Input Validation:** Consider a more robust input validation mechanism, especially for the name fields (e.g., checking for valid characters).
- **Error Handling:** Instead of Toast messages, provide more visually integrated error messages or feedback within the UI.
- **Code Organization:** Extract styling values into a separate file or object to make the code more maintainable.
- **State Management:** Explore alternative state management solutions like `ViewModel` if your app's complexity increases.
- **Navigation:** Handle navigation to other screens using `NavController` more explicitly.

**Revised Code with Improvements (Example)**

```kotlin
@Composable
fun AddStudentScreen(viewModel: StudentViewModel = viewModel(), navController: NavController) { 

    // ... (rest of the code with input fields and button)

                    // Button to add student
                    Button(
                        // ...
                        onClick = {
                            viewModel.addStudent(firstName, lastName, studentId)
                            if (viewModel.errorState.value) {
                                // Handle errors (e.g., show a Snackbar)
                            } else {
                                onStudentAdded()
                                navController.navigate("student_list_screen") // Example navigation
                            }
                        },
                        // ...
                    ) 
}
```

In this example, a `ViewModel` (`StudentViewModel`) could be used to handle the logic for adding students and managing any potential errors, making the UI code cleaner.



Feel free to ask if you have any specific questions or want further refinements to the code!



## Announcements Screen

Absolutely! Let's break down the `AnnouncementsScreen` composable function and provide a detailed documentation of its functionality, structure, and components.

**AnnouncementsScreen: A Comprehensive Overview**

This composable is responsible for displaying announcements within a class portal app. It includes features like:

*   **Announcement Display:** Show a list of announcements, each with details (date, title, description, student who posted).
*   **Add Announcement:**  Provides a dialog for users to create new announcements.
*   **Edit Announcement:**  Allows editing of existing announcements.
*   **Delete Announcement:**  Enables deletion of announcements.
*   **Notification:**  Display a notification for the latest announcement.
*   **Navigation:**  Allows navigation to other screens, such as the home screen ("dashboard").
*   **Clear All:**  Provides an option to clear all announcements.

**Structure and Components**

1.  **Scaffold:**
    *   The foundation for the screen's layout.
    *   Contains a `TopAppBar` for the title and actions (more icon, dropdown menu), and the main content area.
    *   A floating action button (FAB) is added to trigger the add announcement dialog.

2.  **State Variables:**
    *   `announcements`: A list of `Announcement` objects to store and display.
    *   `expanded`: Controls the visibility of the dropdown menu.
    *   `showAddDialog`, `showEditDialog`: Determine whether the add/edit dialogs are shown.
    *   `currentId`: Keeps track of the next available ID for new announcements.
    *   `selectedAnnouncementIndex`: Stores the index of the currently selected announcement for editing.
    *   `clickedIndex`: Indicates which announcement is currently expanded to show details.
    *   `notificationVisibleState`: Manages the visibility of the notification card.
    *   `announcementbackbrush`:  Defines a gradient brush for the background.

3.  **LaunchedEffect (Initialization):**
    *   Triggers when the composable enters the composition.
    *   Loads existing announcements from a file (`loadAnnouncement`) and populates the `announcements` list.
    *   Determines the next available `currentId`.

4.  **Functions:**
    *   `addAnnouncement`: Adds a new announcement to the list and saves it to the file.
    *   `deleteAnnouncement`: Removes an announcement at a given index and saves the changes.
    *   `editAnnouncement`: Updates an existing announcement and saves the changes.
    *   `AnnouncementDialog`: A reusable composable for both adding and editing announcements.
    *   `titleTextStyle` and `descriptionTextStyle`: Composable functions for styling text consistently.

5.  **TopAppBar and Dropdown Menu:**
    *   Contains the screen title and an icon button to show the dropdown menu.
    *   The menu offers options to navigate to the home screen or clear all announcements.

6.  **Announcement List:**
    *   Iterates through the `announcements` list to display each announcement as a `Card`.
    *   Clicking on a card toggles the visibility of its details.
    *   When details are visible, buttons for editing and deleting are shown.
    *   The first announcement is displayed as a notification card (`NotificationCard`).
    *   If there are no announcements, a message indicating this is displayed.

7.  **Floating Action Button:**
    *   Clicking it sets `showAddDialog` to true, opening the dialog to add a new announcement.

8.  **Dialog Logic:**
    *   When either `showAddDialog` or `showEditDialog` is true, the `AnnouncementDialog` is displayed.
    *   It handles collecting the announcement details and confirming/canceling the action.
    *   After adding or editing an announcement, a notification is displayed (`notificationVisibleState.value = true`).

**Potential Improvements:**

*   **State Management:** Consider using a `ViewModel` to manage the state of announcements, especially if your app's complexity grows.
*   **Navigation:** Instead of hardcoding "dashboard", use a more generic way to navigate to the home screen (e.g., through a navigation graph).
*   **Error Handling:** Add error handling for file operations (loading and saving).
*   **UI Enhancements:**
    *   Improve the appearance of the announcement cards and dialogs.
    *   Add animations for smoother transitions.

Let me know if you have any other questions.




### Assignments Screen
Absolutely! Let's break down the `AssignmentScreen` code and provide detailed documentation, explaining its components and functionality:

**AssignmentScreen Composable**

This composable is responsible for managing assignments organized into units within a class portal app. It offers features for creating, editing, deleting, and viewing assignments.

**Structure and Components**

1. **State Variables:**
    - `units`: A list of `UnitData` objects, each containing a unit name and a list of associated assignments.
    - `showUnitDialog`, `showAssignmentDialog`: Flags to control the visibility of the dialogs for adding/editing units and assignments.
    - `currentUnit`, `currentAssignment`: Hold the data of the currently selected unit and assignment for editing.
    - `editUnitIndex`, `editAssignmentIndex`: Store the index of the unit or assignment being edited.
    - `showwarning`: Controls the visibility of an alert dialog warning the user to select a unit before adding an assignment.
    - `expandedMenu`: Manages the visibility of the dropdown menu on the top app bar.
    - `pagerState`: Manages the state of the horizontal pager that displays unit assignments.

2. **LaunchedEffect:**
    - This effect is used to update the `currentUnit` variable whenever the `pagerState.currentPage` changes. It ensures that the `currentUnit` always reflects the unit that is currently being displayed in the pager.

3. **Scaffold:**
    - Sets up the basic layout structure with a top app bar and the main content area.
    - The `TopAppBar` has a title, a back navigation button, and a dropdown menu with options to:
        - Add Unit
        - Edit Unit
        - Delete Unit
        - Add Assignment

4. **AlertDialog for Warning:**
    - If the user tries to add an assignment without selecting a unit, this dialog is shown to alert them.

5. **ScrollableTabRow and HorizontalPager:**
    - The `ScrollableTabRow` is used to display tabs for each unit.
    - The `HorizontalPager` displays the assignments of the currently selected unit.

6. **AssignmentItemRow:**
    - A reusable composable to display each assignment within a unit.
    - Provides options to expand the assignment for more details, edit it, or delete it.

7. **AddEditUnitDialog and AddEditAssignmentDialog:**
    - These are dialogs used for adding new or editing existing units and assignments.
    - They take the necessary data as input and save it using `FileUtil.saveUnitsAndAssignments()`.

**Functionality**

1. **Loading Data:**
    - When the screen is composed, unit and assignment data is loaded from a file using `FileUtil.loadUnitsAndAssignments()`.

2. **Navigation:**
    - A back button in the top bar allows navigating back to the dashboard.
    - The dropdown menu provides options to interact with units and assignments.
    - The `HorizontalPager` lets the user swipe between different units.

3. **Adding/Editing Units and Assignments:**
    - The "Add Unit" and "Add Assignment" options open their respective dialogs.
    - The "Edit Unit" option opens the unit dialog with the data of the currently selected unit.
    - The "Delete Unit" option removes the current unit and saves the changes.
    - Clicking on the edit icon in an `AssignmentItemRow` opens the assignment dialog with the assignment's data.

4. **Saving Data:**
    - Changes to units and assignments are saved to the file after each operation using `FileUtil.saveUnitsAndAssignments()`.

**Potential Improvements:**

- **Error Handling:** Implement error handling for file operations (loading and saving).
- **UI Enhancements:**
    - Add a visual indicator for the currently selected tab in the `ScrollableTabRow`.
    - Consider using `LazyColumn` for better performance when dealing with a large number of assignments.
- **State Management:** Explore alternative state management solutions like `ViewModel` for better organization and scalability.

Let me know if you have any other questions.




### Record Attendance Screen
Absolutely! Let's break down the `RecordAttendanceScreen` composable and provide comprehensive documentation with explanations and potential enhancements:

**RecordAttendanceScreen Composable**

This composable is designed to facilitate attendance recording for students across different units within a class or course. It utilizes a tabbed interface for unit selection and provides checkboxes to mark student attendance.

**Structure and Components**

1. **State Variables:**

    - `daysOfWeek`: A string variable calculated based on the current day to display in the app bar title.
    - `units`: A mutable state list containing unit names loaded from a file (`FileUtil.loadUnitsAndAssignments()`).
    - `students`: A list of students loaded from a file (`FileUtil.loadStudents()`).
    - `pagerState`: Manages the state of the `HorizontalPager` used to display different units.
    - `attendanceRecords`: A mutable state map to track attendance for each student in each unit.
    - `checkboxStates`: A mutable state map to control whether checkboxes are enabled (true initially, false after being checked).
    - `addbackbrush`: A gradient brush for styling the background.

2. **LaunchedEffect:**

    - Initializes the `attendanceRecords` and `checkboxStates` maps when the composable is created.
    - For each unit and student, it checks if an attendance record exists. If not, it creates a new entry with default values (false for attendance, true for checkbox enabled).

3. **Scaffold:**

    - The main structural element for the screen.
    - Contains the following components:
        - `TopAppBar`: Displays the screen title (including the day of the week) and has the following actions:
            - Back button: Navigates back to the previous screen.
            - Save button: Triggers saving the attendance records to a file.

4. **ScrollableTabRow and HorizontalPager:**

    - `ScrollableTabRow`: Displays tabs for each unit in the `units` list. When a tab is selected:
        - `pagerState.scrollToPage()` is called (within a coroutine) to switch to the corresponding page in the `HorizontalPager`.
        - The text color of the selected tab is changed to `globalcolors.textColor`.
    - `HorizontalPager`: Displays the content for each unit (in this case, a `LazyColumn` to show the students).

5. **LazyColumn:**

    - Efficiently displays the list of students for the currently selected unit.
    - For each student, it creates a `Row` with:
        - The student's name (displayed as text).
        - A checkbox to mark their attendance. The state of the checkbox (`checked`) is controlled by the `attendanceRecords` map.
        - When a checkbox is checked:
            - The corresponding value in the `checkboxStates` map is set to false to disable it.
            - The corresponding value in the `attendanceRecords` map is set to true to indicate attendance.

6. **Save Button Logic:**

    - Collects all attendance records from the `attendanceRecords` map.
    - Creates `AttendanceRecord` objects for each student and unit.
    - Saves all the attendance records to a file using `FileUtil.saveAttendanceRecords()`.
    - Calls the `onAttendanceRecorded` lambda to notify the parent composable about the changes.

**Potential Enhancements**

*   **State Management (ViewModel):** Use a `ViewModel` to manage the state of `units`, `students`, `attendanceRecords`, and other data. This will make the code more organized and testable.
*   **Navigation:** Use a navigation graph to define navigation flows and pass data between composables.
*   **Error Handling:** Implement error handling for file operations (loading and saving) and display appropriate messages to the user.
*   **UI Enhancements:**
    *   Consider adding a search bar to filter students.
    *   Allow users to select the date for which attendance is being recorded.
    *   Add visual feedback after saving attendance (e.g., a confirmation message).

Let me know if you have any other questions.


### Attendance Report Screen
Absolutely! Let's break down the `AttendanceReportScreen` code and provide a comprehensive documentation with explanations and areas for potential improvement:

**AttendanceReportScreen Composable**

This composable is responsible for presenting an attendance report for students across different units within a course. It allows filtering by student name and date and displays statistics like total present, total absent, and attendance percentage for each student in a selected unit.

**Structure and Components**

1. **Scaffold:**
    - The main structural component of the screen.
    - Contains a `TopAppBar` with a title (`Attendance Report`) and a navigation icon to go back.

2. **AttendanceReportContent:**
    - The core composable that displays the attendance report content. It's structured as follows:

        - **State Variables:**
            - `students`: A mutable state list of `Student` objects, loaded from the file system using `FileUtil.loadStudents()`.
            - `originalStudents`: A snapshot of the initial `students` list, used for filtering.
            - `allAttendanceRecords`: A list of all `AttendanceRecord` objects, loaded from the file system.
            - `units`: A mutable state list of unit names, derived from the loaded units and assignments.
            - `pagerState`: Manages the state of the `HorizontalPager` used to display different units.
            - `searchQuery`: Holds the text entered into the search bar.
            - `selectedDate`: Stores the currently selected date for filtering.
            - `dateFormatter`: A `DateTimeFormatter` for formatting dates.
            - `expanded`: Controls the visibility of the dropdown menu for date selection.
            - `addbackbrush`: A gradient brush for styling the background.

        - **Tab Row and Pager:**
            - `ScrollableTabRow`: Displays tabs for each unit, allowing the user to switch between them. It uses `pagerState` to control the currently selected tab.
            - `HorizontalPager`: Displays the attendance report content for the currently selected unit.

        - **Attendance Data Processing:**
            - `filteredAttendanceRecords`: Filters the `allAttendanceRecords` based on the selected unit and date.
            - `studentAttendance`: A list of `StudentAttendance` objects, each containing information about a student's attendance (total present, total absent, attendance percentage).

        - **Filtering:**
            - A search bar with `OutlinedTextField` allows filtering the list of students by name or ID.
            - `ExposedDropdownMenuBox` and `ExposedDropdownMenu` are used to filter the attendance report by date. The available dates are dynamically determined from the `allAttendanceRecords`.

        - **LazyColumn:**
            - Efficiently displays the list of `StudentAttendance` objects.
            - Displays a header row with column labels ("Name", "Present", "Absent", "Percent").
            - Displays a row for each `StudentAttendance` object, with the student's name, total present, total absent, and attendance percentage.
            - The attendance percentage is color-coded based on predefined thresholds.
            - A divider separates each row.

3. **StudentAttendance Data Class:**

    - A simple data class to hold information about a student's attendance for a particular unit and date.

**Potential Improvements**

*   **State Management:** Replace the use of `remember` for state variables with a `ViewModel` to better manage the state of this complex screen.
*   **Error Handling:** Add error handling for file operations and filtering operations.
*   **Loading State:** Display a loading indicator while fetching data and filtering results.
*   **Navigation:** Pass the selected unit and date to another screen for detailed analysis or further actions.
*   **UI Enhancements:** Consider adding more filters (e.g., by month or year), or allowing export of the attendance report.


Let me know if you have any other questions.



@Composable
fun Assignments(navController: NavController, viewModel: AssignmentsViewModel = viewModel(), context: Context) {
val selectedUnitIndex = viewModel.selectedUnitIndex.collectAsState()

    // ... (rest of the UI)

    Box(
        // ...
        .clickable { 
            navController.navigate("assignments/${selectedUnitIndex.value}")
        }
    ) { 
        // ...
    }

    // ...
}

// (In another file)
class AssignmentsViewModel : ViewModel() {
val selectedUnitIndex = MutableStateFlow(0)
}


### Colors Settings
Absolutely! Here's a comprehensive documentation of the `ColorSettings` composable function, breaking down its functionality and components:

**ColorSettings Composable**

This composable is designed to allow users to customize the color scheme of an app. It provides input fields for the primary, secondary, tertiary, and text colors, with options to save the changes or revert to the default colors. It also includes validation to ensure that entered color codes are valid.

**Structure and Components**

1. **ColorScheme Data Class:**
    - Represents a color scheme with four properties: `primaryColor`, `secondaryColor`, `tertiaryColor`, and `textColor`.
    - These properties are strings representing the hexadecimal color codes (e.g., "003C43").
    - Provides a `parseColor` function to convert a hex string into a Compose `Color` object.

2. **`globalcolors` Object:**
    - An object that acts as a central repository for the current color scheme.
    - `COLORS_FILE_NAME`: Constant for the name of the file where the color scheme is stored.
    - `defaultScheme`: A `ColorScheme` object defining the default colors.
    - `currentScheme`: A mutable state variable that holds the current color scheme. It's initialized with the default scheme.
    - `loadColorScheme`: Loads a color scheme from a file if it exists; otherwise, returns the default scheme.
    - `saveColorScheme`: Saves the current color scheme to a file.
    - `resetToDefaultColors`: Saves the default color scheme to the file, effectively reverting the colors.
    - `primaryColor`, `secondaryColor`, `tertiaryColor`, `textColor`: Property getters that return the corresponding colors from the `currentScheme`.

3. **ColorSettings Composable:**
    - Accepts a `Context` for file operations and two lambda functions: `onsave` (called when the user saves the colors) and `onrevert` (called when the user reverts to default colors).
    - State Variables:
        - `primaryColor`, `secondaryColor`, `tertiaryColor`, `textColor`: These hold the current color values as strings, reflecting the values in `globalcolors.currentScheme`.
    - LaunchedEffect:
        - When the `globalcolors.currentScheme` changes (e.g., after loading or resetting), it updates the local state variables to match.
    - Layout:
        - Uses a `Column` to arrange the components vertically with even spacing.
        - `OutlinedColorTextField` composables are used for each color input.
        - Two buttons are provided:
            - "Save Colors": Calls `globalcolors.saveColorScheme` with the new colors and invokes the `onsave` lambda.
            - "Revert to Default Colors": Calls `globalcolors.resetToDefaultColors` and invokes the `onrevert` lambda.

4. **OutlinedColorTextField Composable:**
    - A custom composable for color input.
    - Takes parameters for the label, color value, text style, and a callback function `onValueChange`.
    - Uses an `OutlinedTextField` to allow the user to input a color code.
    - `isValidColor`: A state variable to track whether the input color code is valid.
    - Validation:
        - Uses `isValidHexColor` to check if the entered value is a valid hex code.
        - The `isError` property of the `OutlinedTextField` is set to `!isValidColor` to display an error state if the code is invalid.
        - A supporting text message is shown if the color code is invalid.


**Key Features**

*   **Color Scheme Persistence:** The color scheme is loaded from a file and saved to it when the user makes changes, ensuring that the settings are remembered across app sessions.
*   **Default Colors:** If no color scheme file exists or loading fails, the app falls back to a set of default colors.
*   **Input Validation:** The `OutlinedColorTextField` composable includes validation to prevent users from entering invalid color codes.


**Potential Improvements**

*   **Color Picker:** Consider using a color picker component instead of text fields for a more intuitive way to choose colors.
*   **Error Handling:** Add more robust error handling for file operations.
*   **User Feedback:** Provide visual feedback to the user after saving or resetting colors.

Let me know if you have any other questions.


### Current Day Event Screen
Absolutely! Let's break down this `CurrentDayEventsScreen` composable and provide comprehensive documentation to help you understand its structure and functionality.

**CurrentDayEventsScreen Composable**

This composable is designed to display a list of lectures or activities scheduled for the current day, pulled from a timetable stored in a file.

**Structure and Components**

1. **Context Initialization:**
    - `context = LocalContext.current`: Obtains the current context to access resources and system services.

2. **State Variables:**
    - `calendar`: A `Calendar` instance to get the current day of the week.
    - `dayOfWeek`: An integer representing the current day of the week (0 for Sunday, 1 for Monday, etc.).
    - `timetableData`: This loads the timetable data from a file using the `loadTimetable` function.
    - `lecturesToday`: Filters the `timetableData` to get only the events scheduled for the current day.
    - `addbackbrush`: Defines a vertical gradient brush using your custom `globalcolors` for the screen's background.

3. **Scaffold:**
    - Provides a structured layout with a top app bar (`TopAppBar`) and the content area for the list of lectures.
    - `TopAppBar`:
        - Title: "Today's Lectures"
        - Colors: Uses `globalcolors.primaryColor` for the container.

4. **Content Display (Column):**
    - The main content of the screen is displayed within a `Column`.
    - **Empty State:**
        - If there are no lectures today (`lecturesToday.isEmpty()`), a message "No activities Scheduled for today" is shown.
    - **LazyColumn:**
        - If there are lectures, they are displayed in a `LazyColumn`.
        - `itemsIndexed`: Iterates over the `lecturesToday` list.
        - `TimetableItemRow`: A composable (not provided in your code) that likely displays individual lecture details.
        - `Divider`: Adds a visual separator between timetable items.

5. **TimetableItemRow (Not Shown):**
    - This is a separate composable function that you'll need to define. It should take a timetable item as input and display the relevant details (e.g., time, subject, location).
    - The provided code suggests it might have `onEdit` and `onDelete` callbacks, but their implementation would be handled in the `TimetableItemRow` composable itself.

**Functionality**

1. **Load Timetable Data:**
    - When the composable is created, it loads the timetable data from a file using the `loadTimetable` function.

2. **Filter Lectures:**
    - It determines the current day of the week.
    - Filters the timetable data to get only the lectures scheduled for today (`lecturesToday`).

3. **Display Lectures:**
    - If there are no lectures today, it displays a message indicating that.
    - If there are lectures, it renders them in a `LazyColumn` using the `TimetableItemRow` composable.

**Potential Improvements**

*   **Navigation:** Consider adding a navigation icon to the `TopAppBar` that takes the user back to the main timetable screen.
*   **Error Handling:** Add error handling to the `loadTimetable` function to gracefully handle cases where the timetable data might not be available or is invalid.
*   **Visual Enhancements:** Customize the appearance of the `TimetableItemRow` composable to make it more visually appealing. You could add icons, colors, or different layouts to make the information clearer.
*   **Time/Date Formatting:** Apply formatting to the date and time information within `TimetableItemRow` to make it easier for users to read.
*   **Edit/Delete Functionality:** The code has placeholders for `onEdit` and `onDelete` functions in `TimetableItemRow`. Implement these to enable editing and deleting functionality if needed.

Let me know if you have any other questions.




### Dashboard
Absolutely! Let's break down the `Dashboard` composable and provide comprehensive documentation with explanations and insights into potential improvements.

**Dashboard Composable**

This composable serves as the central hub of your application, offering navigation to various sections and displaying information to the user. It includes features like:

-   **Greeting Message:**  Displays a personalized greeting based on the time of day.
-   **Top Announcement:**  Shows the most recent announcement if available.
-   **Scrollable Top Boxes:**
    -   These boxes are designed to catch the user's attention and quickly provide relevant information or actions.
    -   The boxes scroll automatically, showcasing a limited number of items at a time.
-   **Tabbed Navigation:**
    -   A scrollable tab row allows users to switch between different content sections.
    -   Each tab corresponds to a different part of the application (e.g., announcements, attendance, timetable, etc.).
    -   The content is displayed based on the currently selected tab.
-   **Navigation Drawer:**
    -   Provides a side menu that reveals additional navigation options.

**Detailed Component Breakdown**

1.  **State Variables:**
    -   `horizontalScrollState`: Controls the scrolling behavior of the top boxes.
    -   `expanded`: Manages the visibility of the navigation drawer.
    -   `announcements`: Stores the announcements loaded from storage.
    -   `selectedTabIndex`:  Tracks the index of the currently active tab.
    -   `screenWidth`: Stores the width of the screen, used for adjusting tab widths.
    -   `tabRowHorizontalScrollState`: Controls the scrolling of the tab row.
    -   `palleteDialog`: Determines the visibility of the color palette dialog.
    -   `showrestarting`:  Controls the visibility of the dialog prompting the user to restart the app.
    -   `addbackbrush`:  Provides a gradient brush for styling the background.
    -   `greetingMessage`: Holds the calculated greeting message.

2.  **Greeting Message:**
    -   The `getGreetingMessage()` function (not shown in the code) likely generates a greeting message based on the time of day (e.g., "Good morning," "Good afternoon," or "Good evening").

3.  **First Announcement Display:**
    -   Fetches the last (presumably most recent) announcement from the `announcements` list.
    -   Displays either a message indicating the presence of a new announcement or a placeholder message if there are no announcements.

4.  **Top Boxes (`TopBoxes` Composable):**
    -   A custom composable function (not provided in the code) to render the scrollable boxes.
    -   Takes an image resource, description text, navigation route, and the `navController` to handle navigation.
    -   `LaunchedEffect` creates an infinite loop to animate the scrolling of the top boxes.

5.  **Scaffold:**
    -   Sets up the app's structure.
    -   `TopAppBar`: Contains the greeting message, navigation drawer icon, and profile icon.
    -   `DropdownMenu`: Contains various navigation options (settings, attendance, students, timetable, announcement, assignment, color palette, logout).
    -   `content`: The main content area, including the tab row and the content displayed based on the selected tab.

6.  **Color Palette Dialog:**
    -   `AlertDialog`:  A dialog that allows the user to customize the app's color scheme.
    -   `ColorSettings`:  (Not shown in the code) A composable function for selecting colors.
    -   When the user saves the color changes, the `palleteDialog` is closed and a `showrestarting` dialog appears.

7.  **Restarting Dialog:**
    -   `AlertDialog`: Prompts the user to restart the app to apply the new color scheme.
    -   When confirmed, it navigates to the "welcome" screen.

8.  **Tab Row and Content:**
    -   `ScrollableTabRow`: Displays tabs for different sections of the app.
    -   The `indicator` composable customizes the appearance of the tab indicator.
    -   `when` block: Based on the selected tab index, it renders the corresponding content using composables like `AnnouncementTabContent`, `AttendanceTabContent`, etc.

**Potential Improvements**

*   **State Management (ViewModel):** Introduce a `ViewModel` to manage the state of `expanded`, `selectedTabIndex`, `palleteDialog`, `showrestarting`, and the data for top boxes and announcements. This would improve code organization, readability, and testability.
*   **Navigation Graph:** Use a navigation graph to define the app's navigation structure, making it easier to manage transitions and pass data between screens.
*   **Error Handling:** Add error handling mechanisms for file loading, network operations, and other potential failure points.
*   **UI Enhancements:**
    -   Refactor the `DropdownMenu` content into reusable components for better maintainability.
    -   Improve the visual design of the top boxes and dialogs.



Let me know if you have any other questions.


### Delete Student Screen
Absolutely! Let's break down the `DeleteStudentScreen` composable and provide comprehensive documentation to help you understand its structure, functionality, and potential refinements:

**DeleteStudentScreen Composable**

This composable is designed to manage the deletion of student records from a file or database within your class portal application. It presents a list of students, allows users to search for specific students, and provides a confirmation dialog before deleting a selected student.

**Structure and Components**

1. **State Variables:**
    - `students`: A mutable state list holding `Student` objects loaded from `FileUtil.loadStudents(context)`.
    - `originalStudents`: A snapshot of the initial `students` list, used for filtering based on search queries.
    - `studentIdToDelete`, `firstNameToDelete`, `lastNameToDelete`: Store the details of the selected student for deletion confirmation.
    - `showConfirmationDialog`: Controls the visibility of the confirmation dialog.
    - `searchQuery`: Holds the text entered into the search field.
    - `selected`: Indicates whether a student is selected for deletion.
    - `background`: Changes the background color of the selected student row.
    - `addbackbrush`: Defines a vertical gradient brush using your custom `globalcolors` for the screen's background.

2. **Scaffold:**
    - Provides the basic layout structure with a top app bar (`TopAppBar`) and the content area.
    - `TopAppBar`:
        - Title: "Delete Student"
        - Navigation Icon: An arrow icon to navigate back.

3. **LazyColumn:**
    - Displays the list of students efficiently.
    - `itemsIndexed`: Iterates over the `students` list.
    - Each row displays the student's first name, last name, and ID.
    - Clicking on a row:
        - Updates `studentIdToDelete`, `firstNameToDelete`, `lastNameToDelete`.
        - Shows the confirmation dialog (`showConfirmationDialog = true`).
        - Toggles the `selected` state to change the row's background color.

4. **Search Functionality:**
    - An `OutlinedTextField` is provided for searching students by name or ID.
    - When the search query changes, it filters the `students` list based on the query and updates the display.

5. **Confirmation Dialog:**
    - When `showConfirmationDialog` is true, an `AlertDialog` pops up to confirm the deletion of the selected student.
    - Displays the selected student's name and ID.
    - Buttons:
        - "Delete this student": Deletes the student record and reloads the student list from the file.
        - "Cancel": Dismisses the dialog without deleting the student.

**Functionality**

1. **Load Students:**
    - Loads the initial list of students from the file using `FileUtil.loadStudents(context)`.

2. **Search:**
    - Filters the `students` list based on the entered search query.

3. **Select Student:**
    - Clicking on a student row populates the `studentIdToDelete` variable and shows the confirmation dialog.

4. **Delete Student:**
    - If the user confirms the deletion, it deletes the student using `FileUtil.deleteStudent(context, studentIdToDelete)`, reloads the student list, and closes the dialog.

**Potential Improvements**

*   **State Management:** Consider using a `ViewModel` to hold and manage the state of `students`, `searchQuery`, and other variables. This will make the code more organized and testable.
*   **Error Handling:** Add error handling to `FileUtil.deleteStudent` and potentially other operations to gracefully handle cases where deletion might fail.
*   **User Feedback:** Provide visual feedback to the user after deleting a student (e.g., a Snackbar message) to confirm the action.
*   **UI Enhancements:**
    -   Consider adding a delete icon to each row for a more intuitive way to trigger deletion.
    -   Customize the appearance of the confirmation dialog to match your app's design.

Feel free to ask for further clarification or suggestions!


### Edit Student Screen

Absolutely! Let's break down the `EditStudentScreen` composable and provide comprehensive documentation to help you understand its structure, functionality, and potential refinements:

**EditStudentScreen Composable**

This composable is designed to enable editing student records within your class portal application. It allows the user to search for a student by ID, display the student's current details, and modify their first and last name.

**Structure and Components**

1. **State Variables:**
    - `studentId`: Stores the ID of the student to be edited.
    - `newfirstName`, `newlastName`: Hold the updated first and last name values.
    - `studentFound`: Tracks whether the student with the entered ID was found.
    - `showDialog`: Controls the visibility of an alert dialog for displaying error messages.
    - `addbackbrush`: Defines a vertical gradient brush using your custom `globalcolors` for the screen's background.

2. **Scaffold:**
    - Provides the basic layout structure with a top app bar (`TopAppBar`) and the content area.
    - `TopAppBar`:
        - Title: "Edit Student"
        - Navigation Icon: An arrow icon to navigate back.

3. **Main Content (Column):**

    - **Student ID Input:**
        - A `TextField` for the user to enter the student ID.
        - Styling: Custom styling using `globalcolors` and shadow effect.

    - **Find Student Button:**
        - When clicked:
            - Loads the list of students using `FileUtil.loadStudents(context)`.
            - Searches for the student with the entered ID.
            - If found, populates `newfirstName`, `newlastName` with the student's current details and sets `studentFound` to true.
            - If not found, shows an alert dialog using `showDialog`.

    - **Update Form (Conditional):**
        - This section is only visible when `studentFound` is true.
        - Contains two `TextField` components for the updated first and last name.
        - Styling: Similar to the student ID input field.

    - **Update Student Button:**
        - When clicked:
            - Calls `FileUtil.editStudent` to update the student's details in the file.
            - Resets the state variables (`studentFound`, `newfirstName`, `newlastName`).
            - Shows a success toast message.
            - Calls the `onBack` lambda to navigate back to the previous screen.

    - **Error Dialog (Conditional):**
        - Shown when `showDialog` is true (i.e., the student ID was not found).
        - Displays an error message and an "OK" button to dismiss it.

**Functionality**

1. **Search and Display Student:**
    - Allows the user to search for a student by ID.
    - If the student is found, their details are displayed in editable text fields.

2. **Update Student:**
    - Takes the updated first and last name values, combines them with the entered ID, and calls `FileUtil.editStudent` to update the student record.
    - Provides user feedback through a toast message and navigates back to the previous screen.

3. **Error Handling:**
    - If the student ID is not found, it shows an alert dialog to inform the user.

**Potential Improvements**

*   **State Management (ViewModel):** Introduce a `ViewModel` to manage the state of `students`, `studentId`, `newfirstName`, `newlastName`, `studentFound`, and `showDialog`. This would enhance code organization, testability, and scalability.
*   **Error Handling:** Implement more robust error handling for file operations (loading and saving). Consider using sealed classes or result types to represent the different outcomes of operations.
*   **Input Validation:** Add input validation to the text fields, such as checking for empty or invalid names.
*   **Navigation:**  Use a navigation graph for more structured navigation within your app.


Let me know if you have any other questions.


### File Utility

Absolutely! Let's break down the provided code and provide a comprehensive documentation of the `FileUtil` object, explaining its purpose, methods, and how it interacts with the rest of the application.

**FileUtil Object: Comprehensive Documentation**

The `FileUtil` object in your class portal application serves as a utility class for handling file input/output (I/O) operations related to the app's data, such as student information, attendance records, assignments, timetables, and announcements. It leverages Gson for JSON serialization/deserialization to store and retrieve data from the device's file system.

**Core Responsibilities**

1.  **Data Models:**
    *   The code defines data classes representing essential entities within the app:
        *   `Student`:  Stores student registration ID, first name, last name, and username.
        *   `AttendanceRecord`:  Records student attendance (ID, date, presence, and unit).
        *   `TimetableItem`: Stores details about a timetable entry (unit, time, duration, lecturer, venue, day).
        *   `UnitData`: Represents a unit of study, including its name and a list of associated assignments.
        *   `Assignment`:  Stores assignment title and description.
        *   `Announcement`: Holds announcement details (ID, date, title, description, student who posted it).

2.  **Utility Functions:**
    *   `getCurrentDateFormatted()`: Returns the current date formatted as "dd/MM/yyyy."

3.  **File I/O Operations:**
    *   **Constants:**
        *   Define file names for each type of data (assignments, students, timetable, attendance records, announcements).
    *   **Gson Instance:**
        *   Creates a Gson instance for JSON processing.

    *   **loadUnitsAndAssignments(context: Context):**
        *   Loads a list of `UnitData` objects from a JSON file (`units_assignments.json`).
        *   If the file doesn't exist, it returns a default list of unit names.
    *   **saveUnitsAndAssignments(context: Context, data: List<UnitData>):**
        *   Saves a list of `UnitData` objects to the `units_assignments.json` file.

    *   **loadStudents(context: Context):**
        *   Loads a list of `Student` objects from a JSON file (`studentsfile.json`).
    *   **saveStudents(context: Context, students: List<Student>):**
        *   Saves a list of `Student` objects to the `studentsfile.json` file.

    *   **loadAttendanceRecords(context: Context):**
        *   Loads a list of `AttendanceRecord` objects from a JSON file (`attendancerecords.json`).
    *   **saveAttendanceRecords(context: Context, records: List<AttendanceRecord>):**
        *   Saves a list of `AttendanceRecord` objects to the `attendancerecords.json` file.
    *   **clearAttendance(context: Context):** Clears the content of the attendance records file.

    *   **loadAnnouncement(context: Context):**
        *   Loads a list of `Announcement` objects from a JSON file (`announcement.json`).
    *   **saveAnnouncement(context: Context, announcements: List<Announcement>):**
        *   Saves a list of `Announcement` objects to the `announcement.json` file.

    *   **loadTimetable(context: Context):**
        *   Loads timetable data (a list of lists of `TimetableItem` objects) from a JSON file (`timetablefile.json`).
        *   If the file doesn't exist, it returns a list of 7 empty lists for each day of the week.
    *   **saveTimetable(context: Context, timetable: List<List<TimetableItem>>):**
        *   Saves timetable data to the `timetablefile.json` file.

    *   **loadAssignments(context: Context):**
        *   Loads assignments data (a list of lists of `Assignment` objects) from a JSON file (`assignments.json`).
    *   **saveAssignments(context: Context, assignments: List<List<Assignment>>):**
        *   Saves assignments data to the `assignments.json` file.
    *   **getAssignment(context: Context, unitIndex: Int, assignmentIndex: Int):**
        *   Retrieves a specific `Assignment` object given the unit and assignment indices. Returns `null` if the unit or assignment doesn't exist.

    *   **editStudent(context: Context, updatedStudent: Student):**
        *   Edits a student in the `studentsfile.json` file.
    *   **deleteStudent(context: Context, studentId: String):**
        *   Deletes a student from the `studentsfile.json` file.




**Usage and Integration**

This `FileUtil` object is likely used by other composables within your app to load, save, and manipulate data. For instance, a screen for managing student details would use the `loadStudents` and `saveStudents` functions, and a screen for viewing assignments would use the `loadAssignments` function.



Let me know if you have any other questions.


### Login Screen

Absolutely! Let's break down the `LoginScreen` composable and provide comprehensive documentation to help you understand its structure, functionality, and potential refinements:

**LoginScreen Composable**

This composable handles user authentication, providing both login and registration functionality. It's designed to be visually appealing and interactive, guiding users through the authentication process.

**Structure and Components**

1.  **State Variables:**
    *   `password`, `confirmPassword`: Store the values entered in the password and confirm password fields.
    *   `passwordVisibility`, `confirmPasswordVisibility`: Control whether the passwords are shown or hidden.
    *   `isRegistering`: Tracks whether the user is in login mode or registration mode.
    *   `wrongpassword`:  Indicates if there's a password mismatch during registration.
    *   `addbackbrush`:  Defines a vertical gradient brush using your custom `globalcolors` for the screen's background.

2.  **Scaffold:**
    *   Sets up the main structure of the screen.
    *   `TopAppBar`: Contains the title ("Login" or "Register" depending on the mode) and a back button to navigate to the previous screen.

3.  **Main Content (Column):**

    *   **Category Selection:**
        *   Two clickable boxes ("Class Rep" and "Student") allow the user to choose their category.
        *   The selected category is stored in `global.selectedcategory`.

    *   **AnimatedVisibility (Error Message):**
        *   Displays an error message if `wrongpassword` is true (indicating password issues during registration).

    *   **Input Fields (Column):**
        *   Contains `OutlinedTextField` components for:
            *   First name and last name (only visible during registration)
            *   Registration ID (always visible)
            *   Password (always visible)
            *   Confirm Password (only visible during registration)
        *   Trailing icons for password fields allow users to toggle visibility.
        *   Custom colors and shadows are applied for styling.

    *   **Buttons (Row):**
        *   "Login" or "Register" button:
            *   **Login Logic:**  Loads students, checks if a student exists, and validates credentials. Shows an error message if login fails.
            *   **Registration Logic:**  Validates input, checks for existing registration IDs, adds the new student to the list, and switches to login mode.
        *   Styling: Custom colors and shapes.

    *   **Switching Between Login/Register:**
        *   Text and a clickable "Login" or "Register" link allow users to switch between the two modes.
        *   Also, offers to switch between "Student" and "Class Rep" categories.

    *   **Footer:**
        *   A simple text to display developer information.

**Functionality**

*   **Login:**
    *   Authenticates users based on their registration ID and password.
    *   If successful, stores user details in global variables and navigates to the "dashboard."

*   **Registration:**
    *   Registers new users.
    *   Checks if the registration ID already exists.
    *   Validates password match.
    *   Saves the new student details to a file.

**Potential Improvements**

*   **State Management:** Consider using a `ViewModel` to manage the state of input fields, visibility, and other UI elements. This will help separate UI logic from data and make the code more organized.
*   **Error Handling:** Improve error handling by providing more specific error messages for different scenarios (e.g., invalid registration ID format, password not meeting requirements).
*   **Security:** Never store passwords in plain text. Instead, use hashing and salting techniques to secure them.
*   **Navigation:**  If applicable, replace hardcoded navigation routes with a navigation graph.
*   **UI Enhancements:** Consider adding visual feedback during the login/registration process, such as a loading indicator.

Feel free to ask if you have any other questions.


### Main Activity and Navigation Component
Absolutely! Let's break down the `MainActivity` and `NavigationComponent` composables and provide detailed documentation with explanations and areas for potential improvement.

**MainActivity (ComponentActivity)**

*   **Purpose:**
    *   The primary entry point for your Android app when it is launched.
    *   Responsible for setting up the Jetpack Compose content and initializing the app's navigation structure.

*   **Components and Functionality:**

    *   `onCreate(savedInstanceState: Bundle?)`:
        *   This is the main lifecycle method of the activity. It is called when the activity is created.
        *   `setContent`: Initializes the Compose UI and sets up the `NavigationComponent`.
        *   `LaunchedEffect`: Used for loading the saved color scheme from local storage when the activity is created.
        *   `enableEdgeToEdge`: (Optional) If enabled, it might extend your app content to the edges of the screen, providing a more immersive experience.

    *   `NavigationComponent(navController: NavHostController, context: Context)`:
        *   This composable function sets up the navigation structure of the app using `NavHost`.
        *   It defines different screens (composable destinations) and their corresponding routes.
        *   When the user navigates to a specific route, the composable function associated with that route is displayed.

    *   `LoginDialog(navController: NavController)`:
        *   This composable renders an alert dialog that prompts the user to log in if they try to perform a specific action without being logged in.
        *   If the user chooses to log in, they are taken to the login screen using `navController.navigate("login")`.

    *   `DefaultPreview`:
        *   A preview function that provides a visual representation of how the `NavigationComponent` would look. This is useful for UI development.

*   **Navigation Graph (`NavHost`)**
    *   Defines the navigation structure of the app.
    *   Composable Destinations:
        *   `"dashboard"`: The main dashboard screen.
        *   `"AddStudent"`: Screen for adding new students.
        *   `"RecordAttendance"`: Screen for recording attendance.
        *   `"AttendanceReport"`: Screen for viewing attendance reports.
        *   `"DeleteStudent"`: Screen for deleting student records.
        *   `"EditStudent"`: Screen for editing student details.
        *   `"welcome"`: The initial welcome screen.
        *   `"announcements"`: Screen for viewing and managing announcements.
        *   `"login"`: The login screen.
        *   `"timetable"`: Screen for viewing or editing the timetable.
        *   `"assignments"`: Screen for managing assignments.
        *   `"students"`: Screen for displaying a list of students.
        *   `"settings"`: Settings screen for adjusting app preferences.
    *   Each destination is associated with a corresponding composable function that renders the content for that screen.

**Potential Improvements**

*   **State Management (ViewModel):** Use a `ViewModel` to manage the state of `global` object. This would make it easier to share data between composables and ensure a consistent state across the app.
*   **Modularization:** Break down the `NavigationComponent` into smaller, more focused composables to improve readability and maintainability.
*   **Navigation:** Consider using a more explicit navigation scheme, such as passing data between composables using arguments instead of relying on global variables.

Feel free to ask for further clarification or suggestions!

### Notification Card

Absolutely! Here's a comprehensive documentation for the `NotificationCard` composable, explaining its functionality, components, and how it can be used:

**NotificationCard Composable**

This composable is designed to display a notification message in a visually appealing and interactive way. It features:

*   **Swipeable Dismissal:** The user can swipe the notification to the left to dismiss it.
*   **Animated Visibility:**  It appears with a fade-in and expand animation and disappears with a fade-out and shrink animation.
*   **Title and Message Display:**  Shows the title and message of the notification.
*   **Expandable Message:**  If the message is too long, it's initially truncated with an ellipsis ("... Read more").  The user can click on the message to expand it and see the full content.
*   **Optional Image:** Can include an image (in this case, from `R.drawable.student`) for visual representation.

**Detailed Component Breakdown**

1.  **`swipeableState`:**
    *   Keeps track of the horizontal swipe state of the notification card.
    *   `initialValue = 0`:  The initial state (not swiped).
    *   `anchors`:  Defines the swipe anchor points (0 for not swiped, `sizePx` for fully swiped).

2.  **`sizePx`:**
    *   Calculates the pixel size equivalent to 300.dp, which is likely the width of the notification card.

3.  **`visibleState`:**
    *   A `MutableState<Boolean>` variable that controls the visibility of the notification card.  The card is shown when `visibleState.value` is `true`.

4.  **`Box` (Main Container):**
    *   Acts as the main container for the notification card.
    *   `Modifier`:
        *   `fillMaxWidth()`:  Makes the card take the full width of its parent.
        *   `padding()`:  Adds padding around the card.
        *   `offset{}`:  Offsets the card based on the swipe position (`swipeableState.offset`).
        *   `background()`:  Applies a background color.
        *   `padding()`:  Adds padding to the content inside the card.
        *   `swipeable()`:  Enables swipe gestures with the specified state, anchors, and threshold.

5.  **`Row` (Content Layout):**
    *   Arranges the image and text content horizontally.
    *   `Image`:  Displays the student image (or another image you choose).
    *   `Spacer`:  Adds some horizontal spacing between the image and the text.
    *   `Column`:
        *   `Text` (Title): Displays the title of the notification.
        *   `Text` (Message):  Displays the message. If the message is too long, it's truncated. Clicking on the message expands/collapses it.

6.  **`LaunchedEffect` (Automatic Dismissal):**
    *   This effect runs when the composable is initially displayed (`swipeableState.currentValue == 0`).
    *   After a delay of 3 seconds, it sets `visibleState.value` to `false`, hiding the notification card.
    *   Also hides notification if the `swipeableState` reaches the right anchor (`swipeableState.currentValue == 1`).

**Usage**

1.  Create a `MutableState<Boolean>` to track the visibility of the notification.
2.  Pass this state to the `NotificationCard` composable.
3.  Trigger the visibility of the notification card by setting the state to `true`.



**Potential Improvements:**

*   **Customizable Dismissal Direction:** Allow customization of the swipe direction for dismissal (right or left).
*   **Dismiss Animation:**  Add a smooth animation when the card is dismissed.
*   **Action Buttons:**  Include buttons in the notification (e.g., "Mark as read," "Open link") for more interactivity.

Let me know if you have any other questions.


### Notifications

Absolutely! Let's break down the provided code and provide a comprehensive documentation with explanations and insights into potential improvements.

**File: Notification Utility Functions (Kotlin)**

This code snippet defines essential functions for creating notification channels (required for Android 8.0 and above) and for displaying notifications to the user within your Class Portal app.

**Components and Functionality**

1. **Constants:**
    - `CHANNEL_ID`: A unique string identifier for the notification channel. This is used to group related notifications together.

2. **`createNotificationChannel(context: Context)` Function:**
    - **Purpose:** Sets up a notification channel for your app, which is necessary for displaying notifications on Android devices running Android 8.0 (API level 26) or higher.
    - **Steps:**
        - Creates a `NotificationChannel` object with the following properties:
            - `name`: User-visible name of the channel (e.g., "Class Portal Notifications").
            - `descriptionText`: User-visible description of the channel (e.g., "Announcements, reminders, and updates about your classes").
            - `importance`: The importance level of the channel, which affects how the notification is presented (e.g., sound, vibration, etc.). In this case, it's set to `IMPORTANCE_HIGH`.
        - Gets a `NotificationManager` instance from the system.
        - Creates the channel using `notificationManager.createNotificationChannel(channel)`.

3. **`showNotification(context: Context, title: String, message: String)` Function:**
    - **Purpose:**  Displays a notification to the user.
    - **Steps:**
        - Creates an `Intent` to open the `MainActivity` when the notification is tapped.
        - Creates a `PendingIntent` from the `Intent`. This `PendingIntent` is used to define the action that will be taken when the notification is clicked (in this case, opening the app).
        - Creates a `NotificationCompat.Builder` object to build the notification:
            - `setSmallIcon`: Sets the small icon for the notification (usually your app's logo).
            - `setContentTitle`, `setContentText`: Set the title and message of the notification.
            - `setPriority`: Sets the priority to `PRIORITY_HIGH`.
            - `setContentIntent`: Sets the `PendingIntent` that will be executed when the notification is clicked.
            - `setAutoCancel`: The notification will be dismissed when the user taps on it.
            - `setDefaults`: Sets the default notification sound, vibration, and lights.
        - Uses `NotificationManagerCompat.from(context)` to interact with the notification system.
        - **Permission Check:** It checks if the app has permission to post notifications. If permission is not granted, a warning message will show up, but will still show the notification.
            - This is a workaround and should be used only when `POST_NOTIFICATIONS` has been added to the manifest.
        - Finally, it displays the notification using `notify()`.



**Potential Improvements:**

*   **Notification Customization:** Allow users to customize the notification sound, vibration, and other behavior through app settings.
*   **Rich Notifications:** Explore using more advanced features like expandable notifications, images, action buttons, etc.
*   **Error Handling:** Add more robust error handling for notification display (e.g., in case the channel creation fails).
*   **Permissions:** Ensure that the app gracefully handles scenarios where the notification permission is not granted. Use appropriate messages to guide the user to enable the permission.

**Example Usage:**

```kotlin
// In an appropriate place (e.g., when you want to show a notification)
showNotification(context, "New Announcement", "Check out the latest update from your instructor!")
```



Let me know if you have any other questions.


### Settings Screen

Absolutely! Let's break down this `SettingsScreen` composable function and provide a comprehensive documentation with explanations and insights into potential improvements.

**SettingsScreen Composable: Comprehensive Documentation**

This composable is designed to present user settings and preferences within your Class Portal app. It allows users to:

*   **Manage Account Information:**
    *   View and edit their first name, last name, and username.
*   **Configure Preferences:**
    *   Toggle notification settings (not implemented in this version).
    *   Choose whether to display their username or full name.
    *   Enable or disable the edge-to-edge display mode.
    *   Customize the app's color palette.

**Structure and Components**

1.  **Scaffold:**
    *   The foundation of the screen layout.
    *   Includes a `TopAppBar` for the title and navigation icon.
    *   `TopAppBar`:
        *   Title: "Settings"
        *   Navigation Icon: An arrow icon to navigate back.
        *   Background color is set using `globalcolors.primaryColor`.

2.  **Main Content (Column):**

    *   **Account Section:**
        *   `Text`: Header for the account section.
        *   `Column`: Container for account details and edit functionality.
            *   `Row`: Displays the user's name and an edit icon button.
            *   `AnimatedVisibility`: Conditionally shows the edit fields when `expandedColumn` is true.
            *   `CustomTextField`: Reusable composable for text input fields.
            *   `IconButton` (Check Icon): Saves the updated student information when clicked.

    *   **Preferences Section:**
        *   `Text`: Header for the preferences section.
        *   `PreferenceItem`: Reusable composable for individual preference settings.

    *   **About Section:**
        *   `Text`: Header for the about section.
        *   Displays app version information.
        *   Provides a clickable link to contact the developer via WhatsApp using `LocalUriHandler`.

3.  **`CustomTextField` Composable:**
    *   A custom composable for input fields.
    *   Takes the `value`, `onValueChange`, and `label` as parameters.
    *   Uses `BasicTextField` for text input.
    *   Displays the label as placeholder text when the input is empty.

4.  **`PreferenceItem` Composable:**
    *   A reusable composable for a single preference item.
    *   Takes the `label`, `checked` state, and `onCheckedChange` callback as parameters.
    *   Renders a `Row` with a label (`Text`) and a `Switch`.
    *   The switch controls the `checked` state and triggers the `onCheckedChange` callback.

**Functionality**

1.  **Loading Student Data:**
    -   Fetches student data from storage based on the logged-in user's registration ID.
    -   If found, populates the initial values of the name and username fields.

2.  **Editing Account Details:**
    -   Clicking the edit icon expands the input fields for editing.
    -   The user can modify the name and username.
    -   Clicking the checkmark icon saves the changes and updates the displayed name.

3.  **Preferences:**
    -   The "Enable Notifications" feature is not yet implemented (marked as "Coming soon").
    -   The "Show Username" option allows switching between displaying the username or full name.
    -   The "Enable Edge To Edge" option allows toggling the edge-to-edge display mode.
    -   The "Use Custom Color Palette" option opens a dialog to change the colors.

4.  **Color Palette Dialog (`ColorSettings` Composable):**
    -   Presents a dialog for customizing app colors.
    -   Provides input fields for primary, secondary, tertiary, and text colors.
    -   Includes "Save Colors" and "Revert to Default Colors" buttons.



**Potential Improvements:**

*   **State Management (ViewModel):**  Use a `ViewModel` to manage the state of the screen, including the user's details and preferences. This would make it easier to share data and logic between composables.
*   **Navigation:**  Consider using a navigation graph to manage navigation within your app in a more structured way.
*   **Error Handling:** Add more robust error handling for file operations and potential edge cases.
*   **Notification Implementation:** Implement the "Enable Notifications" feature to allow users to toggle notifications.
*   **UI Enhancements:**
    -   Add tooltips or explanations for preference items.
    -   Consider using a color picker for the color palette instead of text fields.

Let me know if you have any other questions.

## Show Students Screen

Absolutely! Let's break down the `ShowStudentsScreen` composable and provide comprehensive documentation to help you understand its structure, functionality, and potential refinements:

**ShowStudentsScreen Composable**

This composable is designed to present a list of students, allowing users to search and sort them. It's a crucial component for managing student records in your Class Portal app.

**Structure and Components**

1. **State Variables:**
    - `students`: A mutable state list holding `Student` objects loaded from `FileUtil.loadStudents(context)`.
    - `originalStudents`: A snapshot of the initial `students` list, used for filtering based on search queries.
    - `searchQuery`: A `TextFieldValue` representing the text entered in the search bar.
    - `addbackbrush`: Defines a vertical gradient brush using your custom `globalcolors` for the screen's background.

2. **Scaffold:**
    - Provides the basic layout structure with a top app bar (`TopAppBar`) and the content area.
    - `TopAppBar`:
        - Title: "Students" (clickable to refresh the student list).
        - Search Bar: An `OutlinedTextField` for searching students.

3. **Main Content (Column):**

    - **Sort By Row:**
        - Displays "Sort by:" text.
        - `Row`: Contains buttons for sorting by registration ID, first name, and last name.
        - When a button is clicked, the `students` list is re-sorted accordingly.

    - **LazyColumn:**
        - Efficiently displays the list of students.
        - Each row:
            - Shows the student's index, first name, last name, and registration ID.
            - Has a bottom border for visual separation.
        - The list is sorted based on the user's selection from the Sort By row.

    - **Total Students Text:**
        - Displays the total number of students in the `students` list.

**Functionality**

1. **Load and Display Students:**
    - Loads the initial list of students from the file using `FileUtil.loadStudents(context)`.
    - Displays the students in the `LazyColumn`.

2. **Search:**
    - Allows users to search by first name, last name, or registration ID.
    - Filters the `students` list based on the search query and updates the display in real time.

3. **Sorting:**
    - Provides buttons to sort the student list by different criteria.
    - Updates the display to reflect the chosen sorting order.

**Potential Improvements**

*   **State Management (ViewModel):** Use a `ViewModel` to hold and manage the state of `students`, `originalStudents`, and `searchQuery`. This will make the code more organized and maintainable.
*   **Error Handling:** Add error handling to `FileUtil.loadStudents` to gracefully handle cases where student data might not be available or is invalid.
*   **Navigation:** Consider adding navigation to a detailed student profile screen when a row is clicked. You could pass the `Student` object as an argument to the next screen.
*   **UI Enhancements:**
    -   Add visual feedback when the search query changes or the sorting is updated.
    -   Customize the appearance of the search bar and buttons to match your app's design.

**Revised Code (Example with ViewModel):**

```kotlin
@Composable
fun ShowStudentsScreen(viewModel: StudentViewModel = viewModel(), navController: NavController) {
    val students by viewModel.students.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // ... (rest of the code with LazyColumn, sorting, etc.)

    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = { viewModel.updateSearchQuery(it) },
        // ...
    )

    // ...
}

class StudentViewModel : ViewModel() {
    val students = MutableStateFlow(FileUtil.loadStudents(context))
    val originalStudents = students.value.toList() // Initial snapshot

    val searchQuery = MutableStateFlow(TextFieldValue(""))

    fun updateSearchQuery(newValue: TextFieldValue) {
        searchQuery.value = newValue
        // Filter students logic here (use originalStudents for filtering)
    }

    // Functions for sorting (update students state accordingly)
}
```

Let me know if you have any other questions!


### Timetable

Absolutely! Let's break down the `Timetable` composable and provide comprehensive documentation with explanations and areas for potential improvement:

**Timetable Composable**

This composable is designed to present and manage a weekly timetable, where each day can have multiple scheduled events (e.g., lectures). It leverages a tabbed layout to switch between days, allows adding and editing timetable items, and persists the data using `FileUtil`.

**Structure and Components**

1. **State Variables:**
    - `days`: A list of days of the week ("Monday" to "Sunday").
    - `dayOfWeek`: The current day of the week, calculated from the `Calendar`.
    - `pagerState`: Manages the state of the `HorizontalPager` used to display different days of the week.
    - `timetableData`: A mutable state list of lists, where each inner list represents the timetable items for a specific day. It's loaded from the `FileUtil`.
    - `showDialog`: Controls the visibility of the dialog used for adding or editing timetable items.
    - `currentDayIndex`:  The index of the currently selected day in the `HorizontalPager`.
    - `editItemIndex`:  The index of the timetable item being edited. If -1, it indicates a new item is being added.
    - `currentItem`: Holds the data of the timetable item currently being edited or added.

2. **Scaffold:**
    - Provides the basic layout structure with a top app bar (`TopAppBar`) and the content area.
    - `TopAppBar`:
        - Title: "Timetable"
        - Navigation Icon: An arrow back icon to return to the dashboard.
        - Action Icon: A "+" icon to add a new timetable item.

3. **ScrollableTabRow and HorizontalPager:**
    - `ScrollableTabRow`: Displays tabs for each day of the week, allowing users to switch between them. It uses `pagerState` to control the currently selected tab.
    - `HorizontalPager`: Displays the timetable content for the currently selected day.

4. **LazyColumn:**
    - Used within the `HorizontalPager` to efficiently display the timetable items for the selected day.
    - `TimetableItemRow`: A composable (defined later in the code) to render each timetable item.
    - `Divider`: Adds visual separators between items.

5. **TimetableItemRow Composable:**
    - Renders the details of a single timetable item: unit, start time, duration, lecturer, and venue.
    - Provides edit and delete buttons.

6. **AddEditTimetableItemDialog Composable:**
    - A dialog for adding new timetable items or editing existing ones.
    - Takes parameters like the `item`, `day`, and callbacks for dismissing and saving.
    - Uses `OutlinedTextField` components for input fields.
    - Provides a dropdown menu to select the unit from a list of available units.

**Functionality**

1. **Load Timetable:**
    - On the initial composition, `loadTimetable(context)` is called to fetch the timetable data from the file system.

2. **Add Timetable Item:**
    - Clicking the "+" icon in the `TopAppBar` opens the `AddEditTimetableItemDialog` for adding a new item.

3. **Edit Timetable Item:**
    - Clicking the edit button on a `TimetableItemRow` opens the `AddEditTimetableItemDialog` in edit mode with the item's data.

4. **Delete Timetable Item:**
    - Clicking the delete button on a `TimetableItemRow` removes the item from the `timetableData` list and saves the updated timetable to the file.

5. **Save Changes:**
    - When the user clicks "Save" in the dialog, the updated or new timetable item is saved to the `timetableData` and the file.

**Potential Improvements**

*   **State Management (ViewModel):** Use a `ViewModel` to manage the state of `timetableData`, `showDialog`, etc. This will improve code organization and maintainability.
*   **Navigation:**  Consider using a navigation graph for more structured navigation within your app.
*   **Error Handling:** Add error handling to file operations (loading and saving).
*   **UI Enhancements:**
    -   Consider using a time picker for the start time input.
    -   Improve the visual appearance of the timetable items and the dialog.
*   **Validation:** Add validation to the input fields in the dialog (e.g., check for valid times).

Let me know if you have any other questions.


### Welcome Screen

Absolutely! Let's break down the `WelcomeScreen` composable and provide comprehensive documentation to help you understand its structure, functionality, and potential refinements:

**WelcomeScreen Composable**

This composable functions as a splash screen for your Class Portal app. It displays a welcome message with an animated progress indicator, simulating a loading process before navigating the user to the login screen.

**Structure and Components**

1. **State Variables:**
    - `startAnimation`:  Triggers the fade-in animation when the composable is displayed.
    - `fadeOut`:  Triggers the fade-out animation after a delay.
    - `showProgress`:  Controls the visibility of the progress indicator.
    - `alphaAnim`:  An animated float value used to control the opacity (alpha) of the "CLASS PORTAL" text during fade in/out.
    - `progress`: An animated float value used for the CircularProgressIndicator.
    - `counter`:  A derived state that calculates the progress percentage based on the progress value.

2. **LaunchedEffect:**
    - Executes a sequence of actions with delays:
        - Starts the fade-in animation (`startAnimation = true`).
        - Waits for 5 seconds.
        - Shows the progress indicator (`showProgress = true`).
        - Waits for another 3 seconds.
        - Starts the fade-out animation (`fadeOut = true`).
        - Waits for 2 seconds.
        - Navigates to the "login" screen, clearing the back stack to the "splash" screen.

3. **Column (Main Layout):**
    - The main container arranging the content vertically.
    - `Modifier`:
        - `fillMaxSize`:  Fills the entire screen.
        - `background`:  Sets the background color using `globalcolors.primaryColor`.

4. **Text ("CLASS PORTAL"):**
    - The large welcome text.
    - `Modifier.alpha(alphaAnim.value)`: Controls the opacity based on the `alphaAnim` animation.
    - `Style`:
        - Large font size, extra bold weight, and serif font family.
        - Applies a shadow effect for a visual pop.

5. **AnimatedVisibility (Progress Indicator):**
    - Conditionally shows the progress indicator when `showProgress` is true.
    - Uses fade and vertical expand/shrink animations.
    - `CircularProgressIndicator`:  Displays the circular progress bar.
    - `Text` (Progress Percentage): Shows the progress as a percentage.
    - `Text` (Loading Message): Displays a message that changes based on the `counter` value to provide user feedback.

**Functionality**

1. **Animation and Transition:**
    - The composable first fades in the "CLASS PORTAL" text.
    - After a delay, the progress indicator appears and starts animating.
    - Once the progress is complete, everything fades out, and the composable navigates to the "login" screen.

2. **Progress Indicator:**
    - Provides a visual representation of the loading process with a circular progress bar and a percentage display.
    - The message below the progress bar dynamically updates to give the user a sense of what's happening during the "loading" process.



**Potential Improvements:**

*   **Theme Integration:**  Customize the colors and fonts to match your app's overall theme.
*   **Lottie Animation:** Replace the simple progress bar with a Lottie animation for a more engaging experience.
*   **Navigation Data:** If needed, consider passing data (like user preferences) from the splash screen to the login screen using navigation arguments.
*   **Testing:** Add tests to ensure that the animations and navigation work as expected.

Let me know if you have any other questions.



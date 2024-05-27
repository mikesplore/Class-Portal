
**LOGIN/REGISTRATION SCREEN**

This code defines a login/registration screen for the Class Portal. It provides a user interface
where users can choose to log in or register as either a "Class Rep" or a "Student." The screen 
consists of various interactive elements and components that handle user input, validation, data storage, and navigation.

**Composable Structure**

The core composable function is `LoginScreen`. Here's a breakdown of its main components:

1. **Scaffold:** Provides a basic app structure with a `TopAppBar` and a `Column` for the main content.

2. **TopAppBar:** Displays the title ("Login" or "Register" depending on `isRegistering`) and a back button for navigation that will take you back to the welcome screen.

3. **Main Content Column:**
    - **Category Selection:** A `Row` containing two `Box` composables that act as buttons. The user taps to choose "Class Rep" or "Student," updating `global.selectedcategory.value`. The selected category is visually highlighted.

    - **Input Fields:**
        - `OutlinedTextField` components for:
            - Registration ID (shown for both login and registration)
            - Password (shown for both login and registration)
            - First Name and Last Name (shown only during registration)
        - The visibility of the password fields can be toggled.
        - Input validation is performed using regular expressions to check the registration ID format.

    - **Login/Register Button:**
        - A `Button` that triggers either the login or registration process depending on `isRegistering`.
        - Registration logic:
            - Validates the input (required fields, matching passwords, valid reg ID).
            - Loads existing students from `FileUtil.loadStudents()`.
            - Checks if the registration ID already exists.
            - If not, adds the new student to the list and saves it using `FileUtil.saveStudents()`.
            - Displays a success toast message and switches to login mode.
        - Login logic:
            - Loads existing students.
            - Finds the student with the matching registration ID and password.
            - If found, displays a success message, sets the `global.loggedinuser.value` to the student's name, and navigates to the "dashboard" screen.
            - If not, displays an error message.

4. **Bottom Section:**
    - **Toggle Mode Text:** Provides a clickable text that allows the user to switch between login and registration modes.
    - **Category Toggle Text:** Offers a clickable text to toggle the selected category between "Class Rep" and "Student."

**State Management**

The `LoginScreen` uses `remember` to manage state variables:

- `password`, `confirmPassword`: Text field values for passwords.
- `passwordVisibility`, `confirmPasswordVisibility`: Booleans to control password visibility.
- `isRegistering`: Boolean that determines whether the screen is in login or registration mode.
- `pattern`: A regular expression for validating the registration ID format.

The `global` object is used to store global app state:

- `selectedcategory.value`:  Stores the selected category ("Class Rep" or "Student").
- `loggedinuser.value`: Stores the name of the logged-in user (set after successful login).

**Helper Functions:**

* `parsecolor`: Converts a hex color string to a `Color` object.
* `loadStudents`, `saveStudents`: Functions (likely in the `FileUtil` class) to manage student data in local storage.

**Code Improvements and Considerations:**

* **File Handling:** It appears that the file-handling functions (e.g., `FileUtil.saveStudents()`) and data classes for `Student` and `Assignment` are defined elsewhere. Make sure these are correctly implemented to handle file reading/writing and data serialization.
* **State Hoisting:** Consider lifting the state variables like `isRegistering` and `selectedCategory` to a `ViewModel` for better state management.
* **Password Security:** Never store passwords in plain text. Use a secure hashing algorithm (e.g., bcrypt) to hash and store passwords.

**Overall:**

This composable provides a functional login/registration screen for an Android app built with Jetpack Compose. 
It effectively handles user input, validation, and navigation while providing a visually appealing interface. 




**ADD STUDENT SCREEN**

**Purpose:**

This composable function is responsible for displaying a screen where users can add new student records to the application. 
It includes input fields for collecting student information, a search feature for filtering existing students, and a button to trigger the student addition process.

**Components:**

1. **Scaffold:**
    - Provides the basic structure for the screen, including a `TopAppBar` and a `Column` to hold the content.

2. **TopAppBar:**
    - Displays a title bar with the text "Add Student."
    - Includes a back button (using `IconButton`) to navigate back to the "dashboard" screen.

3. **Main Content Column:**
    - **Input Fields (CustomTextField):**
        - Three custom composables (`CustomTextField`) are used to gather student information: first name, last name, and registration ID.
        - These composables likely provide styled text fields with appropriate labels and error handling. (The implementation of `CustomTextField` is not shown in the provided code).

    - **Search Bar (OutlinedTextField):**
        - Allows the user to search for existing students by their registration ID.
        - The `onValueChange` callback filters the `filteredStudents` list based on the entered text.
        - If the search query is blank, the original list of students is restored.

    - **Search Results (LazyColumn):**
        - A `LazyColumn` displays the filtered list of students (`filteredStudents`).
        - Each item in the list is represented by a `Text` composable showing the student's details (index, first name, last name, and registration ID).
        - The list is bordered with a 1.dp gray line.

    - **Add Button (Button):**
        - Triggers the process of adding a new student.
        - Checks if the input fields are filled and if the registration ID matches a specific pattern using a regular expression (`pattern`).
        - If valid:
            - Loads existing students from a file using `FileUtil.loadStudents()`.
            - Creates a new `Student` object and adds it to the loaded list.
            - Saves the updated list back to the file using `FileUtil.saveStudents()`.
            - Clears the input fields.
            - Shows a "Student added successfully" toast message.
            - Calls the `onStudentAdded` function (presumably to refresh other parts of the UI).
        - If invalid:
            - Shows a "Please enter a valid student ID" toast message.


**State Management:**

- `firstName`, `lastName`, `studentId`: `MutableState` variables to store the values entered in the text fields.
- `originalStudents`: A list to store the original, unfiltered student data loaded from the file.
- `filteredStudents`: A list that gets updated with the filtered student data based on the search query.
- `pattern`: A regular expression to validate the student ID format.
- `addBackBrush`: A `Brush` object used to create the gradient background of the screen.



**Additional Notes and Potential Improvements:**

- **File Handling:** The provided code assumes the existence of a `FileUtil` class (or object) with `loadStudents` and `saveStudents` functions to handle the file-based storage of student data. Ensure that this class is implemented correctly.
- **Error Handling:** Consider adding more robust error handling to the file reading/writing operations and the student ID validation.
- **Search Refinements:** You could expand the search functionality to search by other criteria, like first or last name, or implement a more sophisticated filtering mechanism (e.g., fuzzy search).
- **UI Enhancements:**
    - The layout could be improved with better spacing and organization.
    - Consider using a more visually appealing design for the search results list (e.g., using cards or a different layout).
- **State Management:** Explore using `ViewModels` to handle the state of the screen, as this would make the code more testable and maintainable.



Let me know if you have any other questions!


# Dapic Attendance App

A comprehensive Android attendance tracking application built with Jetpack Compose, designed for Dapic employees to log their work activities and site visits.

## 🚀 Features

- **Employee Authentication**: Secure login with employee credentials
- **Dynamic Form Flow**: Intelligent screen navigation based on work allocation type
- **Work Allocation Tracking**: Support for Office, Site Visit, WFH, and other work types
- **Google Sheets Integration**: Automatic data submission to Google Sheets
- **Firebase Analytics**: Comprehensive usage tracking and analytics
- **Modern UI**: Material Design 3 with Jetpack Compose

## 📱 Screen Flow & Navigation

The app follows a dynamic navigation pattern where the next screen is determined by the user's selections in previous screens. Here's the complete flow:

![App Navigation Flowchart](app_navigation_flowchart.png)

*App Navigation Flowchart showing the dynamic screen flow and decision points*

### 1. **Splash Screen** (`SPLASH_SCREEN`)
- **Purpose**: App initialization and branding
- **Navigation**: Automatically navigates to Employee Authentication after a brief delay
- **Validation**: None

### 2. **Employee Authentication** (`EMPLOYEE_AUTH_SCREEN`)
- **Purpose**: Employee login and verification
- **Fields**:
    - Employee Name (Dropdown)
    - Password (Text field with visibility toggle)
- **Validation**:
    - Employee name must be selected from dropdown
    - Password must match the employee's stored password
    - Both fields are required
- **Navigation**: Proceeds to Date Selection on successful authentication

### 3. **Date Selection** (`DATE_SCREEN`)
- **Purpose**: Select work date(s)
- **Fields**:
    - Single date picker
    - Date range picker (optional)
- **Validation**:
    - Date must be selected
    - Date range must have valid start and end dates
- **Navigation**: Proceeds to Work Allocation

### 4. **Work Allocation** (`WORK_ALLOCATION_SCREEN`)
- **Purpose**: Define work type and specific allocation
- **Fields**:
    - Work Allocation Type (Dropdown)
    - Work Allocation (Dynamic dropdown based on type)
- **Work Allocation Types**:
    - **Office**: None, Program Development, Panel Testing, Online Support, Other Development
    - **Site Visit**: Service Call, OEM Installation Call, EndUser Installation Call, Sales Call, Panel Testing
    - **WFH**: None, Program Development, Online Support
    - **AB/PH/Travelling/Exhibition**: Direct navigation to final screen
- **Navigation Logic**:
  ```
  If Work Allocation Type is:
  - "AB", "PH", "Travelling", "Exhibition" → REMARK_SCREEN
  - "Office" + "Other Development"/"Program Development" → CURRENT_STATUS_SCREEN
  - "Site Visit" + "Sales Call" → OEM_NAME_SCREEN
  - "Office" + "None" → REMARK_SCREEN
  - "WFH" + "None" → REMARK_SCREEN
  - "Site Visit" + "Exhibition" → REMARK_SCREEN
  - All other combinations → CALL_TYPE_SCREEN
  ```

### 5. **Call Type** (`CALL_TYPE_SCREEN`)
- **Purpose**: Specify the type of call (for site visits)
- **Fields**:
    - Call Type (Dropdown: FOC, Chargeable)
- **Validation**: Call type must be selected
- **Navigation**: Proceeds to OEM Name

### 6. **OEM Name** (`OEM_NAME_SCREEN`)
- **Purpose**: Enter Original Equipment Manufacturer name
- **Fields**:
    - OEM Name (Text input)
- **Validation**: OEM name must not be empty
- **Navigation**: Proceeds to End User Name

### 7. **End User Name** (`END_USER_SCREEN`)
- **Purpose**: Enter end user/client name
- **Fields**:
    - End User Name (Text input)
- **Validation**: End user name must not be empty
- **Navigation Logic**:
  ```
  If Work Allocation Type = "Site Visit" AND Work Allocation = "Sales Call":
    → REMARK_SCREEN
  Else:
    → PANEL_CODE_SCREEN
  ```

### 8. **Panel Code** (`PANEL_CODE_SCREEN`)
- **Purpose**: Enter panel identification code
- **Fields**:
    - Panel Code (Text input)
- **Validation**: Panel code must not be empty
- **Navigation**: Proceeds to Service Request Number

### 9. **Service Request Number** (`SERVICE_REQUEST_SCREEN`)
- **Purpose**: Enter service request/ticket number
- **Fields**:
    - Service Request Number (Text input)
- **Validation**: Service request number must not be empty
- **Navigation**: Proceeds to Current Status

### 10. **Current Status** (`CURRENT_STATUS_SCREEN`)
- **Purpose**: Update current work status
- **Fields**:
    - Current Status (Dropdown: In Progress, Idle, Hold, Complete, Other)
- **Validation**: Current status must be selected
- **Navigation**: Proceeds to Remarks

### 11. **TA Bill** (`TA_BILL_SCREEN`)
- **Purpose**: Enter travel allowance bill details
- **Fields**:
    - TA Bill (Text input)
- **Validation**: Optional field
- **Navigation**: Proceeds to Remarks

### 12. **Remarks** (`REMARK_SCREEN`)
- **Purpose**: Final remarks and form submission
- **Fields**:
    - Remarks (Text area)
- **Validation**: Optional field
- **Actions**:
    - Submit form to Google Sheets
    - Clear form data
    - Navigate back to Employee Authentication

## 🔄 Navigation Rules Summary

### Direct to Final Screen (REMARK_SCREEN)
- Work Allocation Type: "AB", "PH", "Travelling", "Exhibition"
- Office + "None"
- WFH + "None"
- Site Visit + "Exhibition"

### Skip to Current Status
- Office + "Other Development" or "Program Development"

### Skip to OEM Name
- Site Visit + "Sales Call"

### Full Flow (All Screens)
- All other combinations follow the complete flow:
  Work Allocation → Call Type → OEM Name → End User Name → Panel Code → Service Request → Current Status → Remarks

## 📊 Data Flow

1. **Form State Management**: All form data is managed through `FormState` data class
2. **ViewModel**: `FormViewModel` handles all business logic and state updates
3. **Repository Pattern**: `DataRepository` manages data operations and Google Sheets integration
4. **Dependency Injection**: Hilt manages dependencies and provides ViewModels
5. **Analytics**: Firebase Analytics tracks form submissions and user interactions

## 🛠 Technical Stack

- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository Pattern
- **Dependency Injection**: Hilt
- **Navigation**: Jetpack Navigation Compose
- **Networking**: Retrofit for Google Sheets API
- **Analytics**: Firebase Analytics
- **Build System**: Gradle with Kotlin DSL

## 📋 Form Fields

| Field | Type | Validation | Required |
|-------|------|------------|----------|
| Employee Name | Dropdown | Must be valid employee | Yes |
| Password | Text | Must match employee password | Yes |
| Date | Date Picker | Valid date | Yes |
| Work Allocation Type | Dropdown | Must be selected | Yes |
| Work Allocation | Dropdown | Must be selected | Yes |
| Call Type | Dropdown | Must be selected | Conditional |
| OEM Name | Text | Non-empty | Conditional |
| End User Name | Text | Non-empty | Conditional |
| Panel Code | Text | Non-empty | Conditional |
| Service Request Number | Text | Non-empty | Conditional |
| Current Status | Dropdown | Must be selected | Conditional |
| TA Bill | Text | Optional | No |
| Remarks | Text Area | Optional | No |

## 🔐 Security Features

- Employee authentication with password verification
- Secure data transmission to Google Sheets
- Input validation on all required fields
- Form state management prevents data loss

## 📈 Analytics Events

The app tracks the following events:
- Form submission with all field values
- Navigation between screens
- User interactions and form completions

## 🚀 Getting Started

1. Clone the repository
2. Configure Google Sheets API credentials
3. Set up Firebase project for analytics
4. Build and run the application

## 📝 Notes

- The app uses dynamic navigation based on user selections
- Form validation ensures data integrity
- All form data is submitted to Google Sheets for record keeping
- The app supports both single date and date range selections
- Employee credentials are managed through the repository pattern
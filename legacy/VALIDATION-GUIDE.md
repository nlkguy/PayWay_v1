# PayWay Solutions - Comprehensive Validation Guide

## Overview
All forms across the PayWay Solutions banking application now include comprehensive validation with user-friendly error messages. Validations are enforced both on form submission and in real-time.

---

## 1. EMPLOYEE LOGIN (index.html / employee-login.html)

### Fields & Validation:

#### Employee ID
- **Format**: Numeric only
- **Length**: Exactly 7-10 digits
- **Error Message**: "Employee ID must be 7-10 digits"
- **Example**: `1234567` or `9876543210`

#### Password
- **Length**: 6-30 characters
- **Validation**: Required field
- **Error Message**: "Password is required"
- **Notes**: During login, any password is accepted. Strong password requirements enforced during registration.

---

## 2. EMPLOYEE REGISTRATION (employee-register.html)

### Fields & Validation:

#### First Name
- **Length**: 1-50 characters
- **Required**: Yes
- **Error Message**: "First name is required (max 50 characters)"

#### Last Name
- **Length**: 1-50 characters
- **Required**: Yes
- **Error Message**: "Last name is required (max 50 characters)"

#### Email
- **Format**: Valid email address
- **Regex Pattern**: `/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/`
- **Required**: Yes
- **Examples**: `john.doe@example.com`, `user_123@company.co.in`
- **Error Message**: "Please enter a valid email address (e.g., name@example.com)"

#### Password
- **Length**: 8-30 characters
- **Requirements**: 
  - At least 1 uppercase letter (A-Z)
  - At least 1 lowercase letter (a-z)
  - At least 1 number (0-9)
  - At least 1 special character (@$!%*?&)
- **Regex Pattern**: `/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,30}$/`
- **Examples**: `Secure@Pass123`, `MyP@ssword2024`
- **Error Message**: "Password must be 8-30 chars with uppercase, lowercase, number & special char (@$!%*?&)"

#### Confirm Password
- **Must Match**: Password field
- **Required**: Yes
- **Error Message**: "Passwords do not match"

#### Address
- **Length**: 1-100 characters
- **Required**: Yes
- **Error Message**: "Address is required (max 100 characters)"

#### Contact Number
- **Format**: Numeric only
- **Length**: Exactly 10 digits
- **Regex Pattern**: `/^[0-9]{10}$/`
- **Required**: Yes
- **Examples**: `9876543210`, `8765432109`
- **Error Message**: "Contact number must be exactly 10 digits"

---

## 3. CUSTOMER REGISTRATION (customer-create.html)

### Fields & Validation:

#### Customer SSN ID
- **Length**: 1-20 characters
- **Required**: Yes
- **Error Message**: "SSN ID is required"

#### Customer Name
- **Length**: 1-100 characters
- **Required**: Yes
- **Error Message**: "Customer name is required (max 100 characters)"

#### Account Number
- **Length**: 1-50 characters
- **Required**: Yes
- **Error Message**: "Account number is required"

#### IFSC Code
- **Length**: 1-11 characters
- **Required**: Yes
- **Error Message**: "IFSC code is required"

#### Account Balance
- **Type**: Non-negative number
- **Min Value**: 0
- **Required**: Yes
- **Error Message**: "Account balance is required and must be non-negative"

#### Aadhar Card No.
- **Format**: Numeric only
- **Length**: Exactly 12 digits
- **Regex Pattern**: `/^[0-9]{12}$/`
- **Required**: Yes
- **Examples**: `123456789012`, `987654321098`
- **Error Message**: "Aadhar number must be exactly 12 digits (e.g., 123456789012)"
- **Placeholder**: "Enter 12-digit Aadhar (e.g., 123456789012)"

#### PAN Card No.
- **Format**: AAAAA0000A (5 uppercase letters, 4 digits, 1 uppercase letter)
- **Length**: Exactly 10 characters
- **Regex Pattern**: `/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/`
- **Required**: Yes
- **Examples**: `ABCDE1234F`, `NOPQR5678S`
- **Error Message**: "PAN must be 10 chars (format: AAAAA0000A, e.g., ABCDE1234F)"
- **Placeholder**: "PAN format: AAAAA0000A (e.g., ABCDE1234F)"
- **Note**: PAN validation uses strict format checking

#### Date of Birth
- **Type**: Date picker
- **Age Requirement**: Minimum 18 years old
- **Max Date**: Set to today's date minus 18 years (enforced in browser)
- **Required**: Yes
- **Error Message**: "You must be at least 18 years old to create an account"
- **Validation Logic**: 
  ```javascript
  let age = today.getFullYear() - dobDate.getFullYear();
  if (age < 18) {
    showError('dob', 'You must be at least 18 years old to create an account');
  }
  ```

#### Gender
- **Options**: Male, Female, Other
- **Required**: Yes
- **Error Message**: "Gender is required"

#### Marital Status
- **Options**: Single, Married, Divorced, Widowed
- **Required**: Yes
- **Error Message**: "Marital status is required"

#### Email
- **Format**: Valid email address
- **Regex Pattern**: `/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/`
- **Required**: Yes
- **Examples**: `customer@bank.com`
- **Error Message**: "Please enter a valid email address (e.g., name@example.com)"

#### Address
- **Length**: 1-200 characters
- **Required**: Yes
- **Error Message**: "Address is required (max 200 characters)"

#### Contact Number
- **Format**: Numeric only
- **Length**: Exactly 10 digits
- **Regex Pattern**: `/^[0-9]{10}$/`
- **Required**: Yes
- **Error Message**: "Contact number must be exactly 10 digits"

---

## 4. TRANSACTION PROCESSING (transaction.html)

### Fields & Validation:

#### Transaction ID
- **Auto-generated**: Yes
- **Format**: TXN + 9-digit timestamp
- **Example**: `TXN123456789`
- **Read-only**: Yes

#### Customer SSN ID
- **Length**: 1-20 characters
- **Required**: Yes
- **Error Message**: "SSN ID is required"

#### Customer Name
- **Length**: 1-100 characters
- **Required**: Yes
- **Error Message**: "Customer name is required (max 100 characters)"

#### Account ID
- **Length**: 1-50 characters
- **Required**: Yes
- **Error Message**: "Account ID is required"

#### Aadhar Card No.
- **Format**: Numeric only
- **Length**: Exactly 12 digits
- **Regex Pattern**: `/^[0-9]{12}$/`
- **Required**: Yes
- **Error Message**: "Aadhar number must be exactly 12 digits (e.g., 123456789012)"
- **Placeholder**: "Enter 12-digit Aadhar (e.g., 123456789012)"

#### PAN Card No.
- **Format**: AAAAA0000A
- **Length**: Exactly 10 characters
- **Regex Pattern**: `/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/`
- **Required**: Yes
- **Error Message**: "PAN must be 10 chars (format: AAAAA0000A, e.g., ABCDE1234F)"
- **Placeholder**: "PAN format: AAAAA0000A (e.g., ABCDE1234F)"

#### Address
- **Length**: 1-200 characters
- **Required**: Yes
- **Error Message**: "Address is required (max 200 characters)"

#### Transaction Date
- **Type**: Date picker
- **Required**: Yes
- **Error Message**: "Transaction date is required"

#### Contact Number
- **Format**: Numeric only
- **Length**: Exactly 10 digits
- **Regex Pattern**: `/^[0-9]{10}$/`
- **Required**: Yes
- **Error Message**: "Contact number must be exactly 10 digits"

#### Mode of Transaction
- **Options**: Cash, UPI, NEFT, IMPS, Cheque
- **Required**: Yes
- **Error Message**: "Mode of transaction is required"

#### Transaction Amount
- **Type**: Positive number
- **Min Value**: Greater than 0
- **Required**: Yes
- **Error Message**: "Transaction amount must be a positive number"

#### Transaction Type
- **Options**: Credit, Debit
- **Required**: Yes
- **Error Message**: "Transaction type is required"

---

## 5. CUSTOMER UPDATE (customer-update.html)

### Fields & Validation:

**Protected/Read-only Fields**:
- Loan ID (auto-generated, cannot be edited)
- Customer SSN (cannot be edited)
- Customer Name (cannot be edited)
- Loan Amount (cannot be edited)
- Loan Duration (cannot be edited)

**Editable Fields**:

#### Email
- **Format**: Valid email address
- **Required**: Yes
- **Error Message**: "Please enter a valid email address"

#### Contact Number
- **Format**: Numeric only
- **Length**: Exactly 10 digits
- **Required**: Yes
- **Error Message**: "Contact number must be exactly 10 digits"

#### Address
- **Length**: 1-200 characters
- **Required**: Yes
- **Error Message**: "Address is required (max 200 characters)"

#### Gender (if applicable)
- **Options**: Male, Female, Other
- **Required**: Yes

#### Marital Status (if applicable)
- **Options**: Single, Married, Divorced, Widowed
- **Required**: Yes

---

## 6. LOAN REQUEST (loan-request.html)

### Fields & Validation:

#### Customer SSN ID
- **Length**: 1-20 characters
- **Required**: Yes

#### Customer Name
- **Length**: 1-100 characters
- **Required**: Yes

#### Occupation
- **Length**: 1-50 characters
- **Required**: Yes

#### Employer Name
- **Length**: 1-100 characters
- **Required**: Yes

#### Employer Address
- **Length**: 1-200 characters
- **Required**: Yes

#### Email
- **Format**: Valid email address
- **Regex Pattern**: `/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/`
- **Required**: Yes

#### Marital Status
- **Options**: Single, Married, Divorced, Widowed
- **Required**: Yes

#### Contact Number
- **Format**: Numeric only
- **Length**: Exactly 10 digits
- **Required**: Yes

#### Customer Address
- **Length**: 1-200 characters
- **Required**: Yes

#### Loan Amount
- **Type**: Positive number
- **Min Value**: Greater than 0
- **Required**: Yes

#### Loan Duration
- **Type**: Positive number
- **Min Value**: 1 year
- **Max Value**: 30 years
- **Required**: Yes

---

## 7. LOAN UPDATE (loan-update.html)

### Fields & Validation:

**Protected/Read-only Fields**:
- Loan ID
- Customer SSN
- Customer Name
- Loan Amount
- Loan Duration

**Editable Fields**:

#### Email
- **Format**: Valid email address
- **Required**: Yes

#### Contact Number
- **Format**: Numeric only
- **Length**: Exactly 10 digits
- **Required**: Yes

#### Address
- **Length**: 1-200 characters
- **Required**: Yes

---

## General Validation Features

### Real-time Error Clearing
- Error message automatically clears when user corrects the field
- Field border returns to normal state
- Improves UX by providing immediate feedback

### Error Display
- **Style**: Red border on field + red error message below
- **Font Size**: 13px
- **Color**: #e74c3c (red)

### Successful Submission
- Green success modal displays
- Shows summary of submitted data
- Auto-redirects after 300ms

### Field Types & Input Restrictions
- Date inputs use native HTML5 date picker
- Numeric fields use `type="number"` where applicable
- Text fields have `maxlength` attributes
- Email fields use `type="email"`
- Password fields use `type="password"`

---

## Regex Pattern Reference

| Field | Regex Pattern | Example |
|-------|---|---|
| Email | `/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/` | john.doe@example.com |
| 10-digit Phone | `/^[0-9]{10}$/` | 9876543210 |
| 12-digit Aadhar | `/^[0-9]{12}$/` | 123456789012 |
| PAN (Format AAAAA0000A) | `/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/` | ABCDE1234F |
| Password (Strong) | `/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,30}$/` | Secure@Pass123 |

---

## Age Verification Logic

The Date of Birth field automatically enforces age 18+ requirement:

```javascript
// Calculate age from DOB
let age = today.getFullYear() - dobDate.getFullYear();
if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < dobDate.getDate())) {
  age--;
}

// Reject if under 18
if (age < 18) {
  showError('dob', 'You must be at least 18 years old to create an account');
}
```

**Additionally**, the date picker's `max` attribute is set to 18 years ago, preventing users from selecting dates that would make them under 18.

---

## Testing & Sample Data

### Test Login Credentials (Employee Login)
- **Employee ID**: `1234567` (7 digits minimum)
- **Password**: Any non-empty password

### Test Customer Data (Customer Registration)
- **SSN**: `SSN001`
- **Aadhar**: `123456789012` (exactly 12 digits)
- **PAN**: `ABCDE1234F` (format: 5 letters + 4 digits + 1 letter)
- **Phone**: `9876543210` (exactly 10 digits)
- **Email**: `test@example.com`
- **Age**: 18+ years old from current date

### Test Loan Data (Loan Request)
- **Loan ID**: `LOAN001`, `LOAN002`, `LOAN003`
- **Loan Amount**: Any positive number
- **Duration**: 1-30 years

---

## Security Notes

1. **All validations are client-side** - Backend validation should also be implemented
2. **Password strength** is enforced during registration with strict requirements
3. **Email format** is validated to ensure proper format
4. **Aadhar & PAN** use strict format validation
5. **Age verification** prevents account creation for users under 18
6. **10-digit phone number** requirement follows Indian standards
7. **Protected fields** in update forms cannot be modified via UI

---

## Future Enhancements

- [ ] Backend server-side validation
- [ ] Phone number international format support
- [ ] Aadhar/PAN verification API integration
- [ ] Email verification via OTP
- [ ] Two-factor authentication for login
- [ ] Password strength indicator during registration
- [ ] Date range validation for transactions (no future dates)

---

**Last Updated**: January 17, 2026
**Version**: 1.0

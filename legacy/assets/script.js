/*  EMPLOYEE LOGIN  */

function login() {

    let id = document.getElementById("empId").value.trim();
    let pass = document.getElementById("password").value.trim();

    // validation
    if(id === "" || pass === ""){
        alert("Please enter Employee ID and Password");
        return;
    }

    // show success popup
    let msg = document.getElementById("loginMsg");
    if(msg){
        msg.innerText = "Employee Login Successful";
        msg.style.display = "block";
    }

    // redirect to a dashboard
    setTimeout(() => {
        window.location.href = "dashboard.html";
    }, 1200);
}


/* EMPLOYEE REGISTRATION */

// auto-generate 7 digit employee ID
window.onload = function(){

    let autoField = document.getElementById("regEmpId");

    if(autoField){
        autoField.value = Math.floor(1000000 + Math.random() * 9000000);
    }
};


function registerEmployee(){

    let fn = document.getElementById("firstName").value.trim();
    let ln = document.getElementById("lastName").value.trim();
    let email = document.getElementById("email").value.trim();
    let pass1 = document.getElementById("pass1").value.trim();
    let pass2 = document.getElementById("pass2").value.trim();
    let address = document.getElementById("address").value.trim();
    let phone = document.getElementById("phone").value.trim();

    /* ---- required validation ---- */
    if(fn === "" || ln === "" || email === "" || pass1 === "" || pass2 === "" || address === "" || phone === ""){
        alert("All fields are mandatory");
        return;
    }

    /* ---- max length rules (from Excel) ---- */
    if(fn.length > 50 || ln.length > 50){
        alert("First name and Last name must be within 50 characters");
        return;
    }

    if(address.length > 100){
        alert("Address must be within 100 characters");
        return;
    }

    /* ---- email validation ---- */
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if(!emailPattern.test(email)){
        alert("Enter a valid Email ID");
        return;
    }

    /* ---- password match ---- */
    if(pass1 !== pass2){
        alert("Password and Confirm Password must match");
        return;
    }

    /* ---- phone validation ---- */
    if(isNaN(phone) || phone.length !== 10){
        alert("Contact Number must be exactly 10 digits");
        return;
    }

    // success message
    let msg = document.getElementById("regMsg");
    if(msg){
        msg.innerText = "Employee Registration Successful";
        msg.style.display = "block";
    }

    // redirect to login after success
    setTimeout(() => {
        window.location.href = "index.html";
    }, 1500);
}
/* CUSTOMER CREATION  */

// Auto generate customer ID
if(document.getElementById("custId")){
    document.getElementById("custId").value =
        "C" + Math.floor(100000 + Math.random() * 900000);
}

function createCustomer(){

    let ssn = document.getElementById("ssn").value.trim();
    let cid = document.getElementById("custId").value.trim();
    let fn = document.getElementById("fname").value.trim();
    let ln = document.getElementById("lname").value.trim();
    let dob = document.getElementById("dob").value;
    let email = document.getElementById("email").value.trim();
    let phone = document.getElementById("phone").value.trim();
    let aadhaar = document.getElementById("aadhaar").value.trim();
    let pan = document.getElementById("pan").value.trim();
    let bal = document.getElementById("balance").value.trim();
    let addr = document.getElementById("address").value.trim();
    let accType = document.getElementById("accType").value;

    // Required check
    if(!ssn || !fn || !ln || !dob || !email || !phone || !aadhaar || !pan || !bal || !addr || !accType){
        alert("All fields are mandatory");
        return;
    }

    // SSN numeric
    if(isNaN(ssn)){
        alert("SSN must be numeric");
        return;
    }

    // Phone validation
    if(isNaN(phone) || phone.length !== 10){
        alert("Contact Number must be 10 digits");
        return;
    }

    // Aadhaar validation
    if(isNaN(aadhaar) || aadhaar.length !== 12){
        alert("Aadhaar must be 12 digits");
        return;
    }

    // PAN validation
    if(pan.length !== 10){
        alert("PAN must be 10 characters");
        return;
    }

    // Email validation
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if(!emailPattern.test(email)){
        alert("Enter valid Email");
        return;
    }

    // Show success
    let msg = document.getElementById("custMsg");
    msg.innerText = "Customer Creation Successful";
    msg.style.display = "block";

    // Redirect
    setTimeout(()=>{
        window.location.href = "dashboard.html";
    },1400);
}
/* CUSTOMER UPDATE  */

// Fake example data (UI only)
let demoCustomer = {
    id: "C123456",
    fname: "Rahul",
    lname: "Sharma",
    email: "rahul@mail.com",
    phone: "9876543210",
    address: "Mumbai"
};

function searchCustomer(){

    let id = document.getElementById("searchCustId").value.trim();

    if(id === ""){
        alert("Enter Customer ID");
        return;
    }

    // Simulated search result
    if(id !== demoCustomer.id){
        alert("Customer not found (demo search only)");
        return;
    }

    // Show update form
    document.getElementById("updateSection").style.display = "block";

    // Fill demo values
    document.getElementById("upFname").value = demoCustomer.fname;
    document.getElementById("upLname").value = demoCustomer.lname;
    document.getElementById("upEmail").value = demoCustomer.email;
    document.getElementById("upPhone").value = demoCustomer.phone;
    document.getElementById("upAddress").value = demoCustomer.address;
}

function updateCustomer(){

    let fn = document.getElementById("upFname").value.trim();
    let ln = document.getElementById("upLname").value.trim();
    let email = document.getElementById("upEmail").value.trim();
    let phone = document.getElementById("upPhone").value.trim();
    let addr = document.getElementById("upAddress").value.trim();

    if(!fn || !ln || !email || !phone || !addr){
        alert("All fields are required");
        return;
    }

    if(isNaN(phone) || phone.length !== 10){
        alert("Phone must be 10 digits");
        return;
    }

    let msg = document.getElementById("updateMsg");
    msg.style.display = "block";

    setTimeout(()=>{
        window.location.href = "dashboard.html";
    },1400);
}
/* CUSTOMER DELETE  */

// demo hardcoded customer
let deleteDemo = {
    id: "C123456",
    name: "Rahul Sharma",
    email: "rahul@mail.com",
    phone: "9876543210"
};

function findCustomer(){

    let id = document.getElementById("delCustId").value.trim();

    if(id === ""){
        alert("Enter Customer ID");
        return;
    }

    if(id !== deleteDemo.id){
        alert("Customer not found (demo only)");
        return;
    }

    // show summary box
    document.getElementById("customerSummary").style.display = "block";

    document.getElementById("sumName").innerText = deleteDemo.name;
    document.getElementById("sumEmail").innerText = deleteDemo.email;
    document.getElementById("sumPhone").innerText = deleteDemo.phone;
}

function deleteCustomer(){

    if(!confirm("Are you sure you want to delete this customer?")){
        return;
    }

    let msg = document.getElementById("deleteMsg");
    msg.style.display = "block";

    setTimeout(()=>{
        window.location.href = "dashboard.html";
    },1400);
}

/* TRANSACTION PROCESSING  */

function submitTransaction(){

    let txnId = document.getElementById("txnId").value.trim();
    let ssn = document.getElementById("ssn").value.trim();
    let name = document.getElementById("custName").value.trim();
    let acc = document.getElementById("accId").value.trim();
    let aadhaar = document.getElementById("aadhaar").value.trim();
    let pan = document.getElementById("pan").value.trim();
    let addr = document.getElementById("address").value.trim();
    let date = document.getElementById("date").value;
    let phone = document.getElementById("phone").value.trim();
    let mode = document.getElementById("mode").value;
    let amt = document.getElementById("amount").value.trim();
    let type = document.getElementById("type").value;

    if(!txnId || !ssn || !name || !acc || !aadhaar || !pan || !addr || !date || !phone || !mode || !amt || !type){
        alert("All fields are mandatory");
        return;
    }

    if(isNaN(phone) || phone.length !== 10){
        alert("Contact number must be 10 digits");
        return;
    }

    if(isNaN(aadhaar) || aadhaar.length !== 12){
        alert("Aadhaar must be 12 digits");
        return;
    }

    if(isNaN(amt) || amt <= 0){
        alert("Enter valid transaction amount");
        return;
    }

    let msg = document.getElementById("txnMsg");
    msg.innerText = "Transaction Successful";
    msg.style.display = "block";

    setTimeout(()=>{
        window.location.href = "dashboard.html";
    },1400);
}
/* LOAN REQUEST  */

function submitLoan(){

    let ssn = document.getElementById("loanSSN").value.trim();
    let name = document.getElementById("loanName").value.trim();
    let occ = document.getElementById("occupation").value.trim();
    let emp = document.getElementById("employer").value.trim();
    let empAddr = document.getElementById("empAddress").value.trim();
    let email = document.getElementById("loanEmail").value.trim();
    let marital = document.getElementById("marital").value;
    let phone = document.getElementById("loanPhone").value.trim();
    let amt = document.getElementById("loanAmount").value.trim();
    let dur = document.getElementById("loanDuration").value.trim();
    let addr = document.getElementById("custAddress").value.trim();

    if(!ssn || !name || !occ || !emp || !empAddr || !email || !marital || !phone || !amt || !dur || !addr){
        alert("All fields are mandatory");
        return;
    }

    if(isNaN(phone) || phone.length !== 10){
        alert("Contact number must be 10 digits");
        return;
    }

    if(isNaN(amt) || amt <= 0){
        alert("Enter valid loan amount");
        return;
    }

    let msg = document.getElementById("loanMsg");
    msg.style.display = "block";

    setTimeout(()=>{
        window.location.href = "dashboard.html";
    },1400);
}
/* =========================
   LOAN UPDATE / CANCEL (US008)
   ========================= */

// Demo loan data
let demoLoan = {
    ssn: "123456789",
    name: "Rahul Sharma",
    amount: "500000",
    duration: "5",
    phone: "9876543210",
    email: "rahul@mail.com"
};

function searchLoan(){

    let ssn = document.getElementById("loanSearchSSN").value.trim();

    if(ssn === ""){
        alert("Enter Customer SSN ID");
        return;
    }

    if(ssn !== demoLoan.ssn){
        alert("Loan not found (demo only)");
        return;
    }

    document.getElementById("loanSection").style.display = "block";

    document.getElementById("upSSN").value = demoLoan.ssn;
    document.getElementById("upName").value = demoLoan.name;
    document.getElementById("upAmount").value = demoLoan.amount;
    document.getElementById("upDuration").value = demoLoan.duration;
    document.getElementById("upPhone").value = demoLoan.phone;
    document.getElementById("upEmail").value = demoLoan.email;
}

function updateLoan(){

    let amt = document.getElementById("upAmount").value.trim();
    let dur = document.getElementById("upDuration").value.trim();

    if(isNaN(amt) || amt <= 0){
        alert("Enter valid loan amount");
        return;
    }

    if(isNaN(dur) || dur <= 0){
        alert("Enter valid loan duration");
        return;
    }

    let msg = document.getElementById("loanUpdateMsg");
    msg.innerText = "Loan Updated Successfully";
    msg.style.display = "block";

    setTimeout(()=>{
        window.location.href="dashboard.html";
    },1400);
}

function cancelLoan(){

    if(!confirm("Are you sure you want to cancel this loan?")){
        return;
    }

    let msg = document.getElementById("loanUpdateMsg");
    msg.innerText = "Loan Cancelled Successfully";
    msg.style.display = "block";

    setTimeout(()=>{
        window.location.href="dashboard.html";
    },1400);
}

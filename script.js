/* =========================
   EMPLOYEE LOGIN  (US002)
   ========================= */

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

    // redirect to a dummy dashboard
    setTimeout(() => {
        window.location.href = "dashboard.html";
    }, 1200);
}


/* =========================
   EMPLOYEE REGISTRATION (US001)
   ========================= */

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

const FinAuth = {

    isLoggedIn: function() {
        return localStorage.getItem("fin_session") !== null;
    },

    login: function(empId, password) {

        var regex = /^[0-9]{7}$/;

        if (!regex.test(empId)) {
            alert("Employee ID must be exactly 7 digits");
            return false;
        }

        if (!password) {
            alert("Password required");
            return false;
        }

        var session = {
            employeeId: empId,
            time: new Date().toISOString()
        };

        localStorage.setItem("fin_session", JSON.stringify(session));

        return true;
    },

    logout: function() {
        localStorage.removeItem("fin_session");
        window.location.replace("employee-login.html");
    },

    protect: function() {
        if (!localStorage.getItem("fin_session")) {
            window.location.replace("employee-login.html");
        }
    }
};

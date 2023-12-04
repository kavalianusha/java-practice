document.addEventListener("DOMContentLoaded", function () {
    const adminTable = document.getElementById('adminTable').getElementsByTagName('tbody')[0];

    function deleteAdmin(adminId) {
        fetch(`http://localhost:8090/admin/${adminId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.json())
            .then(data => {
                if (data.result.includes('deleted')) {
                    console.log(`Admin with ID ${adminId} has been deleted.`);
                    const row = document.getElementById(`adminRow_${adminId}`);
                    row.remove();
                } else {
                    console.error('Error:', data.result);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    function updateAdmin(adminId) {
        // Redirect to update page with adminId
        window.location.href = `adminUpdate.html?adminId=${adminId}`;
    }

    function showUpdatePopup(adminId) {
        document.getElementById('updatePopup').style.display = 'block';

        document.getElementById('updateYesButton').addEventListener('click', function () {
            updateAdmin(adminId);
        });

        document.getElementById('updateNoButton').addEventListener('click', function () {
            document.getElementById('updatePopup').style.display = 'none';
        });
    }

    fetch('http://localhost:8090/admin/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(admin => {
                const adminTable = document.getElementById('adminTable').getElementsByTagName('tbody')[0];
                const row = adminTable.insertRow();
                const adminIdCell = row.insertCell(0);
                const nameCell = row.insertCell(1);
                const userNameCell = row.insertCell(2);
                const phoneNoCell = row.insertCell(3);
                const emailIdCell = row.insertCell(4);
                const passwordCell = row.insertCell(5);
                const roleCell = row.insertCell(6);
                const actionCell = row.insertCell(7);

                adminIdCell.innerHTML = admin.adminId;
                nameCell.innerHTML = admin.name;
                userNameCell.innerHTML = admin.userName;
                phoneNoCell.innerHTML = admin.phoneNo;
                emailIdCell.innerHTML = admin.emailId;
                passwordCell.innerHTML = admin.password;
                roleCell.innerHTML = admin.role;
                // actionCell.innerHTML = admin.actionCell;

                const editButton = document.createElement('button');
                editButton.type = 'button';
                editButton.classList.add('edit-icon');
                editButton.innerHTML = '<img src="https://icons.veryicon.com/png/o/miscellaneous/linear-small-icon/edit-246.png" class="edit-icon" alt="Edit">';

                editButton.addEventListener('click', function () {
                    const adminId = this.dataset.adminId;
                    showUpdatePopup(adminId);
                });

                // Create and add "Delete" button
                const deleteButton = document.createElement('button');
                deleteButton.type = 'button';
                deleteButton.classList.add('delete-icon');
                deleteButton.innerHTML = '<img src="https://icons.veryicon.com/png/o/miscellaneous/icon-15/icon-delete-3.png" class="delete-icon" alt="Delete">';

                deleteButton.addEventListener('click', function () {
                    const adminId = this.dataset.adminId;
                    document.getElementById('deletePopup').style.display = 'block';

                    document.getElementById('deleteYesButton').addEventListener('click', function () {
                        deleteAdmin(adminId);
                        document.getElementById('deletePopup').style.display = 'none';
                    });

                    document.getElementById('deleteNoButton').addEventListener('click', function () {
                        document.getElementById('deletePopup').style.display = 'none';
                    });
                });

                // Append buttons to the row
                // Assuming actionCell is the cell where you want to put the buttons
                actionCell.appendChild(editButton);
                actionCell.appendChild(deleteButton);
            });
        })
        .catch(error => console.error('Error:', error));
});



  //       document.addEventListener("DOMContentLoaded", function () {
    //         const adminTable = document.getElementById('adminTable').getElementsByTagName('tbody')[0];

    //         function deleteAdmin(adminId) {
    //             fetch(`http://localhost:8090/admin/${adminId}`, {
    //                 method: 'DELETE',
    //                 headers: {
    //                     'Content-Type': 'application/json'
    //                 },
    //             })
    //                 .then(response => response.json())
    //                 .then(data => {
    //                     if (data.result.includes('deleted')) {
    //                         console.log(`Admin with ID ${adminId} has been deleted.`);
    //                         const row = document.getElementById(`adminRow_${adminId}`);
    //                         row.remove();
    //                     } else {
    //                         console.error('Error:', data.result);
    //                     }
    //                 })
    //                 .catch(error => {
    //                     console.error('Error:', error);
    //                 });
    //         }
    //         function updateAdmin(adminId) {
    //             // Redirect to update page with adminId
    //             window.location.href = `adminUpdate.html?adminId=${adminId}`;
    //         }
    // fetch('http://localhost:8090/admin/all')
    //     .then(response => response.json())
    //     .then(data => {
    //         const adminTable = document.getElementById('adminTable').getElementsByTagName('tbody')[0];
    //         data.forEach(admin => {
    //             const row = adminTable.insertRow();
    //             const adminIdCell = row.insertCell(0);
    //             const nameCell = row.insertCell(1);
    //             const userNameCell = row.insertCell(2);
    //             const phoneNoCell = row.insertCell(3);
    //             const emailIdCell = row.insertCell(4);
    //             const passwordCell = row.insertCell(5);
    //             const roleCell = row.insertCell(6);
    //             const actionCell = row.insertCell(7);

    //             adminIdCell.innerHTML = admin.adminId;
    //             nameCell.innerHTML = admin.name;
    //             userNameCell.innerHTML = admin.userName;
    //             phoneNoCell.innerHTML = admin.phoneNo;
    //             emailIdCell.innerHTML = admin.emailId;
    //             passwordCell.innerHTML = admin.password;
    //             roleCell.innerHTML = admin.role;
    //             // actionCell.innerHTML = admin.actionCell;

    //             const editButton = document.createElement('a');
    //             editButton.href = 'adminUpdate.html';
    //             editButton.classList.add('action-button');
    //             editButton.innerHTML = '<img src="https://icons.veryicon.com/png/o/miscellaneous/linear-small-icon/edit-246.png" class="edit-icon" alt="Edit">';

    //             const deleteButton = document.createElement('button');
    //             deleteButton.type = 'button';
    //             deleteButton.classList.add('delete-icon'); // This is correct
    //             deleteButton.innerHTML = '<img src="https://icons.veryicon.com/png/o/miscellaneous/icon-15/icon-delete-3.png" class="delete-icon" alt="Delete">';

    //             // Add click event listeners for edit and delete actions
    //               editButton.addEventListener('click', function () {
    //                         const adminId = this.dataset.adminId;
    //                         updateAdmin(adminId);
    //                     });

    //                     deleteButton.addEventListener('click', function () {
    //                         const adminId = this.dataset.adminId;
    //                         document.getElementById('deletePopup').style.display = 'block';

    //                         document.getElementById('deleteYesButton').addEventListener('click', function () {
    //                             deleteAdmin(adminId);
    //                             document.getElementById('deletePopup').style.display = 'none';
    //                         });

    //                         document.getElementById('deleteNoButton').addEventListener('click', function () {
    //                             document.getElementById('deletePopup').style.display = 'none';
    //                         });
    //                     });
    //             actionCell.appendChild(editButton);
    //             actionCell.appendChild(deleteButton);
    //         });
    //     })
    //     .catch(error => console.error('Error:', error));
    //     });

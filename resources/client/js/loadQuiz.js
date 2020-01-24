function pageLoad(){
    loadQuiz();
    questionName();
}
/*
function loadQuiz() {
    let QuestionsHTML = `<table>` +
        `<tr>` +
        `<th>Quiz Name</th>`+
        `<th>Question ID</th>`
        `</tr>`;
    console.log(QuestionsHTML);
    fetch('/Questions/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Questions => {

        for (let quizName of Questions) {

            QuestionsHTML += `<tr>` +
                `<td>${quizName.quizName}</td>` +
                `<button class='playButton' data-id='${questionID.id}'>Play Quiz</button>` +
                `</td>` +
                `</tr>`;
        }

        QuestionsHTML += '</table>';
        document.getElementById("listDiv").innerHTML = QuestionsHTML;
        let editButtons = document.getElementsByClassName("playButton");
        for (let button of editButtons) {
            button.addEventListener("click", playButton);
        }
    })

}//end of function
*/

function questionName(){
     let QuestionsNameHTML = `<table>` +
         `<tr>` +
         `</tr>`;
     fetch('/Questions/list', {method: 'get'}
     ).then(response => response.json()
     ).then(Questions => {
         for (let question of Questions) {
             QuestionsNameHTML += `<tr>` +
                 `<td>${Questions.question}</td>` +
                 `</tr>`;
         }
         QuestionsNameHTML += `</table>`;
         document.getElementById("listName").innerHTML = QuestionsNameHTML;
     })

}//end of function

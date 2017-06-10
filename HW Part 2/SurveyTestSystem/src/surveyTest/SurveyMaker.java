package surveyTest;

// Responsible for creating surveys
public class SurveyMaker {

	protected AbstractSurveyIO io;
	
	public SurveyMaker(AbstractSurveyIO io) {
		this.io = io;
	}
	
	// Creates a Survey and prompts for its name, number of questions, and 
	public Survey MakeSurvey() throws Exception {
		Survey sv = new Survey(this.io);
		int numQuestions = 0;
		
		this.io.display("Please specify the name of the survey:");
		String name;
		do {
			name = this.io.getUserInput();
			if (name.length() == 0) {
				this.io.display("The name cannot be zero-length");
			}
		} while (name.length() == 0);
		
		sv.setName(name);
		
		// Set the number of questions for this survey
		this.io.display("Please specify the number of questions, which must be greater than 0:");
		do {
			try {
				numQuestions = Integer.parseInt(this.io.getUserInput());
			}
			catch (NumberFormatException e) {
				this.io.display("Invalid input, integer expected");
			}
		} while (numQuestions < 1);
		
		for (int i = 0; i < numQuestions; i++) {
			sv.addQuestion(MakeQuestion());
		}
		
		return sv;
	}
	
	// Creates Questions to be added into a Survey
	public Question MakeQuestion() throws Exception {
		this.io.display("Please select a Question type by entering its corresponding number below:");
		this.io.display("1: True/False");
		this.io.display("2: Multiple Choice");
		this.io.display("3: Short Answer");
		this.io.display("4: Essay");
		this.io.display("5: Ranking");
		this.io.display("6: Matching");
		
		// determine the desired question to be made
		int questionTypeIdx = 0;
		do {
			String inStr = this.io.getUserInput();
			// parseInt will throw an exception if it cannot parse the input
			try {
				questionTypeIdx = Integer.parseInt(inStr);
				if (questionTypeIdx < 1 || questionTypeIdx > 6) {
					this.io.display("Input out of range, options are 1 through 6");
				}
			}
			catch (NumberFormatException e) {
				this.io.display("Invalid input, integer expected");
			}
		} while (questionTypeIdx < 1 || questionTypeIdx > 6);
		
		// Prompt user for question's prompt
		this.io.display("Please enter the question prompt:");
		String prompt = this.io.getUserInput();
		
		// instantiate the desired question with obtained prompt
		Question q = null;
		switch (questionTypeIdx) {
		case 1:
			q = new TrueFalse(prompt);
			break;
		case 2:
			q = new MultipleChoice(prompt);
			break;
		case 3:
			q = new ShortAnswer(prompt);
			break;
		case 4:
			q = new Essay(prompt);
			break;
		case 5:
			q = new Ranking(prompt);
			break;
		case 6:
			q = new Matching(prompt);
			break;
		}
		
		// set the choices/options in the question after its instantiation
		q.setChoices(this.io);
		
		return q;
	}

}

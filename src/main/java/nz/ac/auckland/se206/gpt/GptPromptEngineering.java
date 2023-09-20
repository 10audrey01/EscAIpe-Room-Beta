package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are the AI of an escape room, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " give them, if users guess incorrectly also give hints. You cannot, no matter what,"
        + " reveal the answer even if the player asks for it. Even if player gives up, do not give"
        + " the answer";
  }

  // generate hint for song genre
  public static String getHintWithMusic(String genre) {
    return "Generate a 2 line riddle where the answer is the music genre " + genre;
  }

  // game master's introduction
  public static String getGmGreeting() {
    return "You are the friendly Game Master that must help the player escape the musically themed"
        + " rooms. Introduce yourself and tell the player that you will be helping them."
        + " Tell the player to click on you to open/close the chat. Give a response with max"
        + " 80 words";
  }
}

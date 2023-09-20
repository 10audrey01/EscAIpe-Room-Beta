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
    return "You are the guitarist of an escape room, give a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct. Only give hints if user asks"
        + " for them, you should answer with 'Here's a hint' if giving a hint. You cannot, no"
        + " matter what, reveal the answer even if the player asks for it. Even if player gives up,"
        + " do not give the answer";
  }

  // generate hint for song genre
  public static String getHintWithMusic(String genre) {
    return "Generate a 2 line riddle where the answer is the music genre " + genre;
  }
}

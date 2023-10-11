package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are the guitarist of an escape room, give a short riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct. Only give hints if user asks"
        + " for them. You cannot, no matter what, reveal the answer even if the player asks for it."
        + " Even if player gives up, do not give the answer";
  }

  /**
   * Generates a hint for a music genre riddle.
   *
   * @param genre The music genre for which to create a hint.
   * @return A hint instructing the player to generate a 2-line riddle with the answer as the
   *     specified music genre.
   */
  public static String getHintWithMusic(String genre) {
    return "Generate a 2 line riddle where the answer is the music genre " + genre;
  }

  /**
   * Generates the game master's introduction message.
   *
   * @return The introduction message for the friendly Game Master character.
   */
  public static String getGmGreeting() {
    return "You are the friendly Game Master that must help the player escape the musically themed"
        + " rooms. Introduce yourself and tell the player that you will be helping them."
        + " Tell the player to click on you to open/close the chat and ask for hints if they"
        + " need them. Your response should be at most 2 lines. Act as if you are a real person";
  }

  /**
   * Generates the game master's response to player interaction.
   *
   * @param msg The player's message or interaction.
   * @return The game master's response, which includes a hint if requested by the player.
   */
  public static String getGmInteraction(String msg) {
    return "The player's message is"
        + msg
        + ". Only give hints if players asks for help. If the player asked for help,"
        + getGmHint()
        + ". Otherwise respond without giving any hints";
  }

  /**
   * Generates a hint based on the game's current state and objectives.
   *
   * @return A hint provided by the game master, tailored to the player's progress.
   */
  public static String getGmHint() {
    if (!GameState.isRiddleSolved) {
      return "Tell the player that maybe the guitarist can help them. Your response should be at"
          + " most 2 lines. Start your answer with 'Here's a hint: '";
    } else if (!GameState.isRiddleObjectFound) {
      return "Tell the player that they should find the riddle object. Your response should be at"
          + " most 2 lines. Start your answer with 'Here's a hint: '";
    } else if (!GameState.isGuitarsPlayed) {
      return "Tell the player that the colours on the note might relate to some objects in a room."
          + " Your response should be at most 2 lines. Start your answer with 'Here's a"
          + " hint: '";
    } else if (!GameState.isPianoPlayed) {
      return "Tell the player that the note sequence they found could be played on an instrument."
          + " Your response should be at most 2 lines. Start your answer with 'Here's a"
          + " hint: '";
    } else if (!GameState.isHarpPlayed) {
      return "Tell the player that they should look for hidden circles in each room. Your response"
          + " should be at most 2 lines. Start your answer with 'Here's a hint: '";
    } else { // if all the objectives are completed
      return "Tell the player that they should unlock the door and escape. Your response should be"
          + " at most 2 lines. Start your answer with 'Here's a hint: '";
    }
  }

  /**
   * Generates a message informing the player that they have no available hints.
   *
   * @return A message informing the player that they currently have no hints.
   */
  public static String getGmNoHint() {
    return "Tell the player that it looks like they have no hints.";
  }
}

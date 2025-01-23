package com.example.guessingwordgame
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guessingwordgame.databinding.ActivityGameScreenBinding
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class GameScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameScreenBinding
    var nodeIndex: Int? =1
    @SuppressLint("ResourceType")
    var   currentLevel=1
    private var currentInputIndex = 0
    private lateinit var inputTextViews: List<TextView>
    var globalWord: String? = null
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_game_screen)
        val levelName = intent.getStringExtra("LEVEL_NAME")
        val levelTextView = findViewById<TextView>(R.id.text_level)

        levelTextView.text = levelName
        fun getWordByLevel(xmlContent: String, levelName: String): String? {
            // Parse the XML content
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val inputStream = xmlContent.byteInputStream(Charsets.UTF_8)
            val document = builder.parse(inputStream)
            document.documentElement.normalize()

            // Get all <word> elements
            val wordNodes = document.getElementsByTagName("word")

            // Iterate through the nodes to find the matching level
            for (i in 0 until wordNodes.length) {
                val node = wordNodes.item(i)

                if (node is Element) {
                    val level = node.getAttribute("level")
                    if (level == levelName) {
                        nodeIndex=i
                        globalWord = node.textContent
                        return node.textContent // Return the word value
                    }
                }
            }

            return null // Return null if no match is found
        }

        fun main() {
            // Your XML content
            val xmlContent = """
<?xml version="1.0" encoding="utf-8"?>
<words>
    <word part="easy" level="level 1">air</word>
    <word part="easy" level="level 2">bird</word>
    <word part="easy" level="level 3">sand</word>
    <word part="easy" level="level 4">leaf</word>
    <word part="easy" level="level 5">hand</word>
    <word part="easy" level="level 6">dark</word>
    <word part="easy" level="level 7">clock</word>
    <word part="easy" level="level 8">apple</word>
    <word part="easy" level="level 9">fruit</word>
    <word part="easy" level="level 10">pizza</word>
    <word part="easy" level="level 11">river</word>
    <word part="easy" level="level 12">dance</word>
    <word part="easy" level="level 13">earth</word>

    <word part="medium" level="level 14">above</word>
    <word part="medium" level="level 15">eager</word>
    <word part="medium" level="level 16">habit</word>
    <word part="medium" level="level 17">heavy</word>
    <word part="medium" level="level 18">judge</word>
    <word part="medium" level="level 19">draft</word>
    <word part="medium" level="level 20">beach</word>
    <word part="medium" level="level 21">blame</word>
    <word part="medium" level="level 22">knife</word>
    <word part="medium" level="level 23">empty</word>
    <word part="medium" level="level 24">album</word>
    <word part="medium" level="level 25">angle</word>
    <word part="medium" level="level 26">cycle</word>

    <word part="difficult" level="level 27">believe</word>
    <word part="difficult" level="level 28">leisure</word>
    <word part="difficult" level="level 29">gateway</word>
    <word part="difficult" level="level 30">measure</word>
    <word part="difficult" level="level 31">nervous</word>
    <word part="difficult" level="level 32">license</word>
    <word part="difficult" level="level 33">benefit</word>
    <word part="difficult" level="level 34">density</word>
    <word part="difficult" level="level 35">private</word>
    <word part="difficult" level="level 36">hunter</word>
    <word part="difficult" level="level 37">storage</word>
    <word part="difficult" level="level 38">package</word>
    <word part="difficult" level="level 39">battery</word>

</words>
    """.trimIndent()
            // Get the word and store it in the global variable
            globalWord = getWordByLevel(xmlContent, levelName.toString())
        }
        main()
        val word = globalWord
        val shuffledWord = word?.toCharArray()?.toList()?.shuffled()
        val textViewIds = listOf(
            R.id.text_one,  // Replace with your actual TextView IDs
            R.id.text_two,
            R.id.text_three,
            R.id.text_four,
            R.id.text_five,
            R.id.text_six,
            R.id.text_seven
        )
        for (i in shuffledWord!!.indices) {
            if (i < textViewIds.size) {
                val textView = findViewById<TextView>(textViewIds[i])
                textView.text = shuffledWord[i].toString()
            }
        }
        val letterFrames = listOf(
            findViewById<FrameLayout>(R.id.container_frame),
            findViewById<FrameLayout>(R.id.container_frame1),
            findViewById<FrameLayout>(R.id.container_frame2),
            findViewById<FrameLayout>(R.id.container_frame3),
            findViewById<FrameLayout>(R.id.container_frame4),
            findViewById<FrameLayout>(R.id.container_frame5),
            findViewById<FrameLayout>(R.id.container_frame6)

        )
        inputTextViews = listOf(
            findViewById(R.id.text_input1),
            findViewById(R.id.text_input2),
            findViewById(R.id.text_input3),
            findViewById(R.id.text_input4),
            findViewById(R.id.text_input5),
            findViewById(R.id.text_input6),
            findViewById(R.id.text_input7)
        )
        val letterTextViews = listOf(
            findViewById<TextView>(R.id.text_one),
            findViewById<TextView>(R.id.text_two),
            findViewById<TextView>(R.id.text_three),
            findViewById<TextView>(R.id.text_four),
            findViewById<TextView>(R.id.text_five),
            findViewById<TextView>(R.id.text_six),
            findViewById<TextView>(R.id.text_seven)
        )
        inputTextViews.forEach { it.parent?.let { (it as View).visibility = View.GONE } }
        for (i in globalWord!!.indices) {
            inputTextViews[i].parent?.let { (it as View).visibility = View.VISIBLE }
        }
        letterFrames.forEachIndexed { index, frame ->
            frame.setOnClickListener {
                val textView = letterTextViews[index]
                val letter = textView.text.toString()
                setLetterToInput(letter)
                if (currentInputIndex == globalWord!!.length) {
                    checkInputOrder(globalWord.toString())
                }
            }
        }

    }
    private fun clearInputFields() {
        val inputFields = listOf(
            findViewById<TextView>(R.id.text_input1),
            findViewById<TextView>(R.id.text_input2),
            findViewById<TextView>(R.id.text_input3),
            findViewById<TextView>(R.id.text_input4),
            findViewById<TextView>(R.id.text_input5),
            findViewById<TextView>(R.id.text_input6),
            findViewById<TextView>(R.id.text_input7)
        )
        inputFields.forEach { it.text = "" }
    }
    private fun reduceHeart() {
        val hearts = listOf(
            findViewById<TextView>(R.id.heart1),
            findViewById<TextView>(R.id.heart2),
            findViewById<TextView>(R.id.heart3),
            findViewById<TextView>(R.id.heart4),
            findViewById<TextView>(R.id.heart5)
        )

        // Hide the first visible heart
        for (heart in hearts) {
            if (heart.visibility == View.VISIBLE) {
                heart.visibility = View.GONE
                break
            }
        }
    }
    private fun initializeGame(){
        val word = globalWord
        val shuffledWord = word?.toCharArray()?.toList()?.shuffled()
        val textViewIds = listOf(
            R.id.text_one,
            R.id.text_two,
            R.id.text_three,
            R.id.text_four,
            R.id.text_five,
            R.id.text_six,
            R.id.text_seven
        )
        for (i in shuffledWord!!.indices) {
            if (i < textViewIds.size) {
                val textView = findViewById<TextView>(textViewIds[i])
                textView.text = shuffledWord[i].toString()
            }
        }
        val letterFrames = listOf(
            findViewById<FrameLayout>(R.id.container_frame),
            findViewById<FrameLayout>(R.id.container_frame1),
            findViewById<FrameLayout>(R.id.container_frame2),
            findViewById<FrameLayout>(R.id.container_frame3),
            findViewById<FrameLayout>(R.id.container_frame4),
            findViewById<FrameLayout>(R.id.container_frame5),
            findViewById<FrameLayout>(R.id.container_frame6)
        )
        inputTextViews = listOf(
            findViewById(R.id.text_input1),
            findViewById(R.id.text_input2),
            findViewById(R.id.text_input3),
            findViewById(R.id.text_input4),
            findViewById(R.id.text_input5),
            findViewById(R.id.text_input6),
            findViewById(R.id.text_input7)
        )
        inputTextViews.forEach { it.parent?.let { (it as View).visibility = View.GONE } }
        for (i in globalWord!!.indices) {
            inputTextViews[i].parent?.let { (it as View).visibility = View.VISIBLE }
        }
        val letterTextViews = listOf(
            findViewById<TextView>(R.id.text_one),
            findViewById<TextView>(R.id.text_two),
            findViewById<TextView>(R.id.text_three),
            findViewById<TextView>(R.id.text_four),
            findViewById<TextView>(R.id.text_five),
            findViewById<TextView>(R.id.text_six),
            findViewById<TextView>(R.id.text_seven)
        )
        letterFrames.forEachIndexed { index, frame ->
            frame.setOnClickListener {
                val textView = letterTextViews[index]
                val letter = textView.text.toString()
                setLetterToInput(letter)
                if (currentInputIndex == globalWord!!.length) {
                    checkInputOrder(globalWord.toString())
                }
            }
        }
    }
    private fun setLetterToInput(letter: String,) {
        if (currentInputIndex < inputTextViews.size) {
            val inputTextView = inputTextViews[currentInputIndex]
            inputTextView.text = letter
            currentInputIndex++
        }
    }
    private fun checkInputOrder(globalWord : String) {
        // Concatenate the letters from the input frames
        val enteredWord = inputTextViews.joinToString(separator = "") { it.text.toString() }
        // Compare with globalWord
        if (enteredWord == globalWord) {
            Toast.makeText(
                this,
                "Congratulations! You formed the correct word.",
                Toast.LENGTH_SHORT
            ).show()
       loadNextLevel() } else {
            Toast.makeText(this, "try again", Toast.LENGTH_SHORT).show()
            reduceHeart()
            clearInputFields()

        }
    }
    private fun loadNextLevel() {
        currentLevel++
        val levelTextView = findViewById<TextView>(R.id.text_level)
        levelTextView.text = "Level $currentLevel"
        val xmlContent = """<?xml version="1.0" encoding="utf-8"?>
        <words>
            <word part="easy" level="level 1">air</word>
            <word part="easy" level="level 2">bird</word>
            <word part="easy" level="level 3">sand</word>
            <word part="easy" level="level 4">leaf</word>
            <word part="easy" level="level 5">hand</word>
            <word part="easy" level="level 6">dark</word>
            <word part="easy" level="level 7">clock</word>
            <word part="easy" level="level 8">apple</word>
            <word part="easy" level="level 9">fruit</word>
            <word part="easy" level="level 10">pizza</word>
            <word part="easy" level="level 11">river</word>
            <word part="easy" level="level 12">dance</word>
            <word part="easy" level="level 13">earth</word>
            <word part="medium" level="level 14">above</word>
            <word part="medium" level="level 15">eager</word>
            <word part="medium" level="level 16">habit</word>
            <word part="medium" level="level 17">heavy</word>
            <word part="medium" level="level 18">judge</word>
            <word part="medium" level="level 19">draft</word>
            <word part="medium" level="level 20">beach</word>
            <word part="medium" level="21">blame</word>
            <word part="medium" level="22">knife</word>
            <word part="medium" level="23">empty</word>
            <word part="medium" level="24">album</word>
            <word part="medium" level="25">angle</word>
            <word part="medium" level="26">cycle</word>
            <word part="difficult" level="27">believe</word>
            <word part="difficult" level="28">leisure</word>
            <word part="difficult" level="29">gateway</word>
            <word part="difficult" level="30">measure</word>
            <word part="difficult" level="31">nervous</word>
            <word part="difficult" level="32">license</word>
            <word part="difficult" level="33">benefit</word>
            <word part="difficult" level="34">density</word>
            <word part="difficult" level="35">private</word>
            <word part="difficult" level="36">hunter</word>
            <word part="difficult" level="37">storage</word>
            <word part="difficult" level="38">package</word>
            <word part="difficult" level="39">battery</word>
        </words>""".trimIndent()
        globalWord = getWordByLevel(xmlContent, "level $currentLevel")
        currentInputIndex = 0
        inputTextViews.forEach { it.text = "" }
        initializeGame()
    }

    private fun getWordByLevel(xmlContent: String, levelName: String): String? {
        // Parse the XML content
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val inputStream = xmlContent.byteInputStream(Charsets.UTF_8)
        val document = builder.parse(inputStream)
        document.documentElement.normalize()

        // Get all <word> elements
        val wordNodes = document.getElementsByTagName("word")

        // Iterate through the nodes to find the matching level
        for (i in 0 until wordNodes.length) {
            val node = wordNodes.item(i)
            if (node is Element) {
                val level = node.getAttribute("level")
                if (level == levelName) {
                    return node.textContent // Return the word value
                }
            }
        }
        return null // Return null if no match is found
    }

}
package org.jsantamariap.eh_ho.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_create_topic.*
import org.jsantamariap.eh_ho.R
import org.jsantamariap.eh_ho.data.TopicsRepo
import org.jsantamariap.eh_ho.inflate
import java.lang.IllegalArgumentException

class CreateTopicFragment : Fragment() {

    var interactionListener: CreateTopicInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CreateTopicInteractionListener) {
            interactionListener = context
        } else {
            throw IllegalArgumentException("Context doesn't implement ${CreateTopicInteractionListener::class.java.canonicalName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // provoca que se creen un par de métodos en el ciclo de vida
        // para añadir menus: onCreateOptionsMenu y onOptionsItemSelected
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_create_topic)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // primero se infla y después se llama al super.onCreate...
        inflater.inflate(R.menu.menu_create_topic, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // una forma sería así
        // if (item.itemId == R.id.action_send)
        // y otra es una nueva forma de kotlin que no existe en java
        // que es con when
        when (item.itemId) {
            R.id.action_send -> createTopic()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()

        interactionListener = null
    }

    private fun createTopic() {
        if (isFormValid()) {
            TopicsRepo.addTopic(
                inputTitle.text.toString()
            )
            // esto es un tanto peligroso, pues puede que el fragment no esté
            // en el flujo. La solución es la de siempre, delegar en la actividad
            // que contiene ese fragment para que se encargue ella mediante
            // la creación de un interface
            // fragmentManager?.popBackStack()
            interactionListener?.onTopicCreated()
        } else {
            showError()
        }
    }
    // realizado con un refactor, Ctrl+T
    private fun isFormValid() = inputTitle.text.isNotEmpty() &&
            inputContent.text.isNotEmpty()

    private fun showError() {
        if (inputTitle.text.isEmpty()) {
            //inputTitle.error = "Error, texto hardcodeado, mala idea"
            inputTitle.error = context?.getString(R.string.error_empty)
        }
        if (inputContent.text.isEmpty()) {
            inputContent.error = context?.getString(R.string.error_empty)
        }
    }

    interface CreateTopicInteractionListener {
        fun onTopicCreated()
    }
}
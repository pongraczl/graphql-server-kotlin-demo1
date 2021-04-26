package com.github.pongraczl.example.graphqlserverkotlindemo1.service;

import com.github.pongraczl.example.graphqlserverkotlindemo1.service.model.Creature
import com.github.pongraczl.example.graphqlserverkotlindemo1.service.model.HumanInput
import com.github.pongraczl.example.graphqlserverkotlindemo1.service.model.LifeStatus
import org.springframework.stereotype.Service

@Service
class SocialService {

    private val creatures = arrayListOf<Creature>()

    /* Sample data initialization */
    init {
        addHuman("Bad Guy", "hangman");
        addHuman("Carl the Other", "");
        addHuman("Sarah Connor", "waitress");
    }


    fun getCreatureById(id: String): Creature? {
        return creatures.firstOrNull { creature -> creature.id == id }
    }

    fun getEveryBody(): List<Creature> {
        return creatures
    }

    fun getAllLiving(): List<Creature> {
        return creatures.filter{creature -> creature.lifeStatus == LifeStatus.ALIVE}
    }

    fun addCreature(creature: Creature): Creature {
        creatures.add(creature)
        return creature
    }

    final fun addHuman(name: String, profession: String): Creature {
        return addCreature(Creature.createNewHuman(name, profession))
    }

    fun addHumans(humansToAdd: List<HumanInput>): List<Creature> {
        return humansToAdd.map{human -> addHuman(human.name, human.profession)}
    }

    fun updateCreature(updatedCreature: Creature): Unit {
        val creatureToUpdate: Creature? = getCreatureById(updatedCreature.id)
        if (creatureToUpdate != null) {
            creatures.remove(creatureToUpdate)
            creatures.add(updatedCreature)
        }
    }

}

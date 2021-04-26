package com.github.pongraczl.example.graphqlserverkotlindemo1.service.model;

const val ORIGINALLY_ZOMBIE_ID_PREFIX = "Z"
const val ORIGINALLY_HUMAN_ID_PREFIX = "C"

class Creature private constructor(val id: String, isZombie: Boolean) {

    var name: String = ""
        private set
    var profession: String = ""
        private set
    var lifeStatus: LifeStatus
        private set

    init {
        lifeStatus = if (isZombie) LifeStatus.LIVING_DEAD else LifeStatus.ALIVE
        creatureCounter++
    }

    fun transformIntoZombieIfAlive(): Unit {
        if (lifeStatus == LifeStatus.ALIVE) {
            lifeStatus = LifeStatus.LIVING_DEAD
        }
    }

    fun kill(): Unit {
        lifeStatus = LifeStatus.REALLY_DEAD
    }


    companion object {
        var creatureCounter: Int = 0

        fun createNewZombie(): Creature {
            return Creature(ORIGINALLY_ZOMBIE_ID_PREFIX + creatureCounter, true)
        }

        fun createNewHuman(name: String, profession: String): Creature {
            val creature: Creature = Creature(ORIGINALLY_HUMAN_ID_PREFIX + creatureCounter, false)
            creature.name = name
            creature.profession = profession
            return creature
        }
    }
}

enum class LifeStatus {
    ALIVE, LIVING_DEAD, REALLY_DEAD
}

package com.github.pongraczl.example.graphqlserverkotlindemo1.service

import com.github.pongraczl.example.graphqlserverkotlindemo1.service.model.CombatResult
import com.github.pongraczl.example.graphqlserverkotlindemo1.service.model.Creature
import com.github.pongraczl.example.graphqlserverkotlindemo1.service.model.LifeStatus
import org.springframework.stereotype.Service

@Service
class ApocalypseService(private val socialService: SocialService) {

    fun getZombies(): List<Creature> {
        return socialService.getEveryBody()
                .filter { creature -> creature.lifeStatus == LifeStatus.LIVING_DEAD}
    }

    fun isZombieApocalypseActive(): Boolean {
        return getZombies().isNotEmpty()
    }

    /**
     * Starts Zombie Apocalypse by creating a zombie if none exists
     *
     * @return if starting was necessary (not already active)
     */
    fun startZombieApocalypse(): Boolean {
        val doesZombieApocalypseNeedToBeStarted: Boolean = !isZombieApocalypseActive()
        if (doesZombieApocalypseNeedToBeStarted) {
            addZombies(1)
        }
        return doesZombieApocalypseNeedToBeStarted
    }

    fun addZombies(count: Int): List<Creature> {
        val createdCreatures: MutableList<Creature> = mutableListOf()
        generateSequence {Creature.createNewZombie()}
                .take(count)
                .forEach{
                    createdCreatures.add(it)
                    socialService.addCreature(it)
                }
        return createdCreatures
    }

    /**
     * @param killerId creatureId of the killer
     * @param victimId creatureId of the victim
     * @return list of the killer and the victim
     */
    fun kill(killerId: String, victimId: String): CombatResult {
        val killer: Creature? = socialService.getCreatureById(killerId)
        val victim: Creature? = socialService.getCreatureById(victimId)

        if (killer != null && victim != null
                && checkIfCreatureIsAbleToKill(killer)
                && checkIfCreatureIsAbleToBeKilled(victim)) {

            simplyKillHumanIfKillerIsHuman(killer, victim)
            makeZombieReallyDeadIfKillerIsHuman(killer, victim)
            transformHumanIntoZombieIfKillerIsZombie(killer, victim)
        }

        return CombatResult(killer, victim)
    }

    fun checkIfCreatureIsAbleToKill(creature: Creature): Boolean {
        return creature.lifeStatus != LifeStatus.REALLY_DEAD
    }

    fun checkIfCreatureIsAbleToBeKilled(creature: Creature): Boolean {
        return creature.lifeStatus != LifeStatus.REALLY_DEAD
    }

    private fun simplyKillHumanIfKillerIsHuman(killer: Creature, victim: Creature) {
        if (killer.lifeStatus == LifeStatus.ALIVE
                && victim.lifeStatus == LifeStatus.ALIVE) {
            victim.kill()
        }
    }

    fun makeZombieReallyDeadIfKillerIsHuman(killer: Creature, victim: Creature) {
        if (killer.lifeStatus == LifeStatus.ALIVE
                && victim.lifeStatus == LifeStatus.LIVING_DEAD) {
            victim.kill()
        }
    }

    fun transformHumanIntoZombieIfKillerIsZombie(killer: Creature, victim: Creature) {
        if (killer.lifeStatus == LifeStatus.LIVING_DEAD
                && victim.lifeStatus == LifeStatus.ALIVE) {
            victim.transformIntoZombieIfAlive()
        }
    }
}

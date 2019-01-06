package com.matheusvillela.afugadepixuleco.game;

import com.matheusvillela.afugadepixuleco.game.objects.Dinheiro;
import com.matheusvillela.afugadepixuleco.game.objects.Pneu;
import com.matheusvillela.afugadepixuleco.game.objects.Stairs;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final int levelNum;

    public static Level createLevel(final Logic logic, final int levelNum) {
        Level level = null;
        if (levelNum == 1) {
            level = new Level(levelNum, new LevelSection(new SectionController(logic) {{
                addFixedForegroundThing(new Stairs(logic, Floor.SECOND, Floor.THIRD, 50, Side.LEFT));
            }},
                    LevelSection.FIRST_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.THIRD, 50));
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SECOND_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FIRST, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.SECOND, 50));
                    }},
                            LevelSection.THIRD_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FOURTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic),
                            LevelSection.FIFTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addMortadela(Floor.FIRST, levelNum);
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FOURTH, 50));
                    }},
                            LevelSection.SIXTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic),
                            LevelSection.SEVENTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFixedForegroundThing(new Stairs(logic, Floor.FIRST, Floor.SECOND, 50, Side.RIGHT));
                        addFixedForegroundThing(new Stairs(logic, Floor.THIRD, Floor.FOURTH, 50, Side.RIGHT));
                    }},
                            LevelSection.EIGHT_SECTION_THINGS));
        } else if (levelNum == 2) {
            level = new Level(levelNum, new LevelSection(new SectionController(logic) {{
                addFixedForegroundThing(new Stairs(logic, Floor.SECOND, Floor.THIRD, 50, Side.LEFT));
            }},
                    LevelSection.FIRST_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.THIRD, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.SECOND, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FOURTH, 50));
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SECOND_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FIRST, 50));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 50));
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                    }},
                            LevelSection.THIRD_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addMortadela(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Pneu(logic, Floor.SECOND, 50));
                    }},
                            LevelSection.FOURTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic),
                            LevelSection.FIFTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addMortadela(Floor.FIRST, levelNum);
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                    }},
                            LevelSection.SIXTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 50));
                    }},
                            LevelSection.SEVENTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFixedForegroundThing(new Stairs(logic, Floor.FIRST, Floor.SECOND, 50, Side.RIGHT));
                        addFixedForegroundThing(new Stairs(logic, Floor.THIRD, Floor.FOURTH, 50, Side.RIGHT));
                    }},
                            LevelSection.EIGHT_SECTION_THINGS));
        } else if (levelNum == 3) {
            level = new Level(levelNum, new LevelSection(new SectionController(logic) {{
                addFixedForegroundThing(new Stairs(logic, Floor.SECOND, Floor.THIRD, 50, Side.LEFT));
            }},
                    LevelSection.FIRST_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.THIRD, 50));
                        addFoguete(Floor.SECOND, levelNum);
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SECOND_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 50));
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.THIRD_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addMortadela(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Pneu(logic, Floor.SECOND, 50));
                    }},
                            LevelSection.FOURTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addFoguete(Floor.THIRD, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FIFTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FOURTH, 50));
                        addFoguete(Floor.SECOND, levelNum);
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SIXTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.SECOND, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FIRST, 50));
                    }},
                            LevelSection.SEVENTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFixedForegroundThing(new Stairs(logic, Floor.FIRST, Floor.SECOND, 50, Side.RIGHT));
                        addFixedForegroundThing(new Stairs(logic, Floor.THIRD, Floor.FOURTH, 50, Side.RIGHT));
                    }},
                            LevelSection.EIGHT_SECTION_THINGS));
        } else if (levelNum == 4) {
            level = new Level(levelNum, new LevelSection(new SectionController(logic) {{
                addFixedForegroundThing(new Stairs(logic, Floor.SECOND, Floor.THIRD, 50, Side.LEFT));
            }},
                    LevelSection.FIRST_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.THIRD, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.SECOND, 50));
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SECOND_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 50));
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.THIRD_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addMortadela(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Pneu(logic, Floor.SECOND, 50));
                        addAviao(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FOURTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addFoguete(Floor.THIRD, levelNum);
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FIFTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addAviao(Floor.THIRD, levelNum);
                        addFoguete(Floor.SECOND, levelNum);
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SIXTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 50));
                        addAviao(Floor.SECOND, levelNum);
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FIRST, 50));
                    }},
                            LevelSection.SEVENTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFixedForegroundThing(new Stairs(logic, Floor.FIRST, Floor.SECOND, 50, Side.RIGHT));
                        addFixedForegroundThing(new Stairs(logic, Floor.THIRD, Floor.FOURTH, 50, Side.RIGHT));
                    }},
                            LevelSection.EIGHT_SECTION_THINGS));
        } else if (levelNum == 5) {
            level = new Level(levelNum, new LevelSection(new SectionController(logic) {{
                addFixedForegroundThing(new Stairs(logic, Floor.SECOND, Floor.THIRD, 50, Side.LEFT));
            }},
                    LevelSection.FIRST_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addAviao(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.SECOND, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FIRST, 50));
                    }},
                            LevelSection.SECOND_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.THIRD, 50));
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.THIRD_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addMortadela(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Pneu(logic, Floor.SECOND, 50));
                        addAviao(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FOURTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addFoguete(Floor.THIRD, levelNum);
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FIFTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FOURTH, 50));
                        addAviao(Floor.THIRD, levelNum);
                        addFoguete(Floor.SECOND, levelNum);
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SIXTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 50));
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SEVENTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFixedForegroundThing(new Stairs(logic, Floor.FIRST, Floor.SECOND, 50, Side.RIGHT));
                        addFixedForegroundThing(new Stairs(logic, Floor.THIRD, Floor.FOURTH, 50, Side.RIGHT));
                    }},
                            LevelSection.EIGHT_SECTION_THINGS));
        } else if (levelNum == 6) {
            level = new Level(levelNum, new LevelSection(new SectionController(logic) {{
                addFixedForegroundThing(new Stairs(logic, Floor.SECOND, Floor.THIRD, 50, Side.LEFT));
            }},
                    LevelSection.FIRST_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 25));
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 75));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.THIRD, 50));
                        addFoguete(Floor.SECOND, levelNum);
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SECOND_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 25));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 25));
                        addAviao(Floor.SECOND, levelNum);
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FIRST, 50));
                    }},
                            LevelSection.THIRD_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addMortadela(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Pneu(logic, Floor.SECOND, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.SECOND, 25));
                        addAviao(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FOURTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addFoguete(Floor.THIRD, levelNum);
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FIFTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FOURTH, 50));
                        addAviao(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.SECOND, 50));
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SIXTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 25));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 25));
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SEVENTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFixedForegroundThing(new Stairs(logic, Floor.FIRST, Floor.SECOND, 50, Side.RIGHT));
                        addFixedForegroundThing(new Stairs(logic, Floor.THIRD, Floor.FOURTH, 50, Side.RIGHT));
                    }},
                            LevelSection.EIGHT_SECTION_THINGS));
        }else {
            level = new Level(levelNum, new LevelSection(new SectionController(logic) {{
                addFixedForegroundThing(new Stairs(logic, Floor.SECOND, Floor.THIRD, 50, Side.LEFT));
            }},
                    LevelSection.FIRST_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FOURTH, 50));
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.THIRD, 50));
                        addFoguete(Floor.SECOND, levelNum);
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SECOND_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 25));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 25));
                        addAviao(Floor.SECOND, levelNum);
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.FIRST, 50));
                    }},
                            LevelSection.THIRD_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addMortadela(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Pneu(logic, Floor.SECOND, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.SECOND, 25));
                        addAviao(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FOURTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFoguete(Floor.FOURTH, levelNum);
                        addFoguete(Floor.THIRD, levelNum);
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.FIFTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 25));
                        addAviao(Floor.THIRD, levelNum);
                        addRemovableForegroundThing(new Dinheiro(logic, Floor.SECOND, 50));
                        addMortadela(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SIXTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.FOURTH, 25));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 75));
                        addRemovableForegroundThing(new Pneu(logic, Floor.THIRD, 25));
                        addAviao(Floor.SECOND, levelNum);
                        addFoguete(Floor.FIRST, levelNum);
                    }},
                            LevelSection.SEVENTH_SECTION_THINGS),
                    new LevelSection(new SectionController(logic) {{
                        addFixedForegroundThing(new Stairs(logic, Floor.FIRST, Floor.SECOND, 50, Side.RIGHT));
                        addFixedForegroundThing(new Stairs(logic, Floor.THIRD, Floor.FOURTH, 50, Side.RIGHT));
                    }},
                            LevelSection.EIGHT_SECTION_THINGS));
        }
        return level;
    }

    private List<LevelSection> sections = new ArrayList<>();

    public Level(int levelNum, LevelSection... sections) {
        for (int i = 0; i < sections.length; i++) {
            this.sections.add(sections[i]);
        }
        this.levelNum = levelNum;
    }

    public List<LevelSection> getSections() {
        return sections;
    }
}

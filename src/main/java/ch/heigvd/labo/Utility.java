package ch.heigvd.labo;

public final class Utility {
    public static final String YAML_TEMPLATE = "indexfile: #inserer nom de fichier index\n" +
            "title:     #inserer titre du site\n" +
            "sourcedir: #inserer repertoire source (probablement www/)\n" +
            "datadir: .\n" +
            "filesdir:  #inserer repertoire contenant les fichiers\n";

    public static String MD_TEMPLATE = "<!--Template pour une page, à modifier comme vous" +
            " le souhaiter ! (format Markdown) \n" +
            "Syntaxe utile: \n *italique*\n **gras**\n tableau 2 lignes 2 colonnes:\n" +
            "|      |      |\n" +
            "| ---- | ---- |\n" +
            "|      |      | \n" +
            "`code` \n bloc de code: \n ````html \n une image (ne pas oublier de copier l'image dans le répértoire" +
            " ../metadonnee/image.png:\n ![Description](./image.png)-->\n\n" +
            "> titre:\n\n" + "> auteur:\n\n" + "> date:\n\n" + "-----\n" +
            "#TITRE 1\n" + "##TITRE 2\n" + "###TITRE 3\n" +
            "Contenu de l'article\n\n" + "![Une image](./image.png)\n";

    public static String MD_INDEX = "#BIENVENUE SUR VOTRE SITE STATIQUE !!!! \n";
}

class PlayerStatus {

    private String nickname;
    private int score;
    private int lives;
    private int health = 100;
    private String weaponInHand;
    private double positionX;
    private double positionY;
    private static String gameName;

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getWeaponInHand() {
        return this.weaponInHand;
    }

    public boolean setWeaponInHand(String weaponInHand) {
        if (weaponInHand.equals("knife") || weaponInHand.equals("sniper") || weaponInHand.equals("kalashnikov")) {
            if (weaponInHand.equals("knife") && score >= WeaponsCost.knife) {
                this.weaponInHand = weaponInHand;
                score -= WeaponsCost.knife;
                return true;
            } else if (weaponInHand.equals("sniper") && score >= WeaponsCost.sniper) {
                this.weaponInHand = weaponInHand;
                score -= WeaponsCost.sniper;
                return true;
            }else if (weaponInHand.equals("kalashnikov") && score >= WeaponsCost.kalashnikov) {
                this.weaponInHand = weaponInHand;
                score -= WeaponsCost.kalashnikov;
                return true;
            } else
                return false;
        }
        return false;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public static String getGameName() {
        return gameName;
    }

    protected static void setGameName(String gameName) {
        PlayerStatus.gameName = gameName;
    }

    public void initPlayer(String player1) {
        this.nickname = player1;
    }

    public void initPlayer(String player1, int i, int i1) {
        this.nickname = player1;
        this.lives = i;
        this.score = i1;
    }

    public void initPlayer(String player2, int i) {
        this.nickname = player2;
        this.lives = i;
    }

    public void findArtifact(int artifactCode) {

        if(isPerfect(artifactCode)) {
            score = score + 5000;
            lives = lives + 1;
            health = 100;
        } else if (isPrime(artifactCode)) {
            score = score + 1000;
            lives = lives + 2;
            health = health + 25;
            if( health > 100)
                health = 100;

        } else if (isEven(artifactCode) && artifactCode % 3 == 0) { // daca nr e div cu 3 => suma cifrelor div cu 3
            score = score - 3000;
            health = health - 25;

            if(health <= 0){
                lives = lives - 1;
                if (lives == 0) {
                    System.out.println("Game Over");
                }
                health = 100;
            }

        } else {
            score = score + artifactCode;
        }
    }

    private boolean isPerfect(int n)
    {
        int sum = 1;
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0) {
                if (i * i != n)
                    sum = sum + i + n / i;
                else
                    sum = sum + i;
            }
        return sum == n && n != 1;
    }

    private boolean isPrime(int n){
        if (n <= 1)
            return false;

        else if (n == 2)
            return true;

        else if (n % 2 == 0)
            return false;

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }


    private boolean isEven (int i) {
        return i % 2 == 0;
    }

    public void movePlayerTo(double i, double i1) {
        this.positionX = i;
        this.positionY = i1;
    }

    public boolean shouldAttackOpponent(PlayerStatus player2) {
        if( this.getWeaponInHand().equals(player2.getWeaponInHand())) {
            double probabilityPlayer1 = (double) (3 * this.health + this.score / 1000) / 4;
            double probabilityPlayer2 = (double) (3 * player2.health + player2.score / 1000) / 4;
            return probabilityPlayer1 >= probabilityPlayer2;
        } else {
            if (distance(player2) > 1000) {
                if(this.getWeaponInHand().equals("sniper")) {
                    return true;
                } else if (player2.getWeaponInHand().equals("sniper")) {
                    return false;
                } else if (this.getWeaponInHand().equals("kalashnikov") && player2.getWeaponInHand().equals("knife")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if(this.getWeaponInHand().equals("kalashnikov")) {
                    return true;
                } else if (player2.getWeaponInHand().equals("kalashnikov")) {
                    return false;
                } else if (this.getWeaponInHand().equals("sniper") && player2.getWeaponInHand().equals("knife")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private double distance(PlayerStatus player2) {
        return Math.sqrt(Math.pow(this.positionX - player2.positionX, 2.0) + Math.pow(this.positionY - player2.positionY, 2.0));
    }
}
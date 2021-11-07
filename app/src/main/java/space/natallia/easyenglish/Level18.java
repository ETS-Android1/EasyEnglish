package space.natallia.easyenglish;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Level18 extends AppCompatActivity {
    //Переменные для картинок
    private int numLeftUpper;
    private int numRightUpper;
    private int numLeftLower;
    private int numRightLower;
    private Array array = new Array(); //Создали новый объект из класса Array
    private ArrayList<Integer> listLearned = new ArrayList<Integer>();
    private Random random = new Random(); //Для генерации случайных чисел
    private int count;//Счетчик правильных ответов
    private int i;//Инднкс текущего изучаемого слова в массиве
    private Dialog dialogEnd;

    //Определяем спрашиваемое слово на английском
    public void getWordEnglish(int numLeftUpper, int numLeftLower, int numRightUpper,
                               int numRightLower, TextView text, int[] textEng) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(numLeftUpper);
        list.add(numRightUpper);
        list.add(numLeftLower);
        list.add(numRightLower);
        Collections.shuffle(list);
        //Если слова из списка изучены, пытаемся выбрать следующее для изучения из списка
        for (int a = 0; a < 4; a++) {
            i = list.get(a);
            if (listLearned.contains(i) == false) {
                break;
            }
        }
        text.setText(textEng[i]);
    }

    //Получаем индекс в массиве левой верхней картинки
    public  int getNumLeftUpper(ImageView img_left_upper, TextView text_left_upper, int[] images,
                                int[] textRus){
        numLeftUpper = random.nextInt(10); //Генерируем случайное число от 0 до 9
        img_left_upper.setImageResource(images[numLeftUpper]);//Достаем соответствующую картинку из массива
        text_left_upper.setText(textRus[numLeftUpper]); //Достаем из массива перевод
        return  numLeftUpper;
    }

    //Получаем индекс в массиве правой верхней картинки
    public  int getNumRightUpper(ImageView img_right_upper, TextView text_right_upper, int[] images,
                                 int[] textRus, int numLeftUpper){
        numRightUpper = random.nextInt(10);
        while (numRightUpper == numLeftUpper) {
        numRightUpper = random.nextInt(10);
    }
        img_right_upper.setImageResource(images[numRightUpper]);
        text_right_upper.setText(textRus[numRightUpper]);
        return numRightUpper;
    }

    //Получаем индекс в массиве левой нижней картинки
    public  int getNumLeftLower(ImageView img_left_lower, TextView text_left_lower, int[] images,
                                int[] textRus, int numLeftUpper, int numRightUpper){
        numLeftLower = random.nextInt(10);
        while (numLeftLower == numLeftUpper || numLeftLower == numRightUpper) {
            numLeftLower = random.nextInt(10);
        }
        img_left_lower.setImageResource(images[numLeftLower]);
        text_left_lower.setText(textRus[numLeftLower]);
        return  numLeftLower;
    }

    //Получаем индекс в массиве правой нижней картинки
    public  int getNumRightLower(ImageView img_right_lower, TextView text_right_lower, int[] images,
                                 int[] textRus, int numLeftUpper, int numRightUpper, int numLeftLower){
        numRightLower = random.nextInt(10);
        while (numRightLower == numLeftUpper || numRightLower == numRightUpper ||
                numRightLower == numLeftLower) {
            numRightLower = random.nextInt(10);
        }
        //Исключаем повторение изучаемых слов
        if (listLearned.contains(numLeftUpper) && listLearned.contains(numRightUpper) &&
                listLearned.contains(numLeftLower) && listLearned.contains(numRightLower)){
            for (int num = 0; num < 10; num++){
                if (listLearned.contains(num) == false) {
                numRightLower = num;
                break;
                }
            }
        }
        img_right_lower.setImageResource(images[numRightLower]);
        text_right_lower.setText(textRus[numRightLower]);
        return numRightLower;
    }

    //Получаем произношение
    public MediaPlayer getPronunciation(int[] pronunciation){
        int pron = pronunciation[i];
        MediaPlayer sound = MediaPlayer.create(this, pron);
        return sound;
    }

    //Проиграть звук
    public void soundPlay (MediaPlayer sound){
        sound.start();
    }

    //Кнопка звука нажать
    public void pronunciationBtnClick(ImageView pronunciationBtn){
        pronunciationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer sound = getPronunciation(array.pronunciation18);
                soundPlay(sound);
            }
        });
    }

    //Получить все картинки
    public  void  getImages(ImageView img_left_upper, ImageView img_right_upper, ImageView img_left_lower,
                         ImageView img_right_lower, TextView text_left_upper, TextView text_right_upper,
                         TextView text_left_lower, TextView text_right_lower, int[] images,
                         int[] textRus){
        getNumLeftUpper(img_left_upper, text_left_upper, images, textRus);
        getNumRightUpper(img_right_upper, text_right_upper, images,
        textRus, numLeftUpper);
        getNumLeftLower(img_left_lower, text_left_lower, images, textRus, numLeftUpper,
        numRightUpper);
        getNumRightLower(img_right_lower, text_right_lower, images,textRus, numLeftUpper,
        numRightUpper, numLeftLower);
    }

    //Разблокируем другие картинки
    public void toUnblockOtherImages(ImageView image1, ImageView image2, ImageView image3){
        image1.setEnabled(true);
        image2.setEnabled(true);
        image3.setEnabled(true);
    }

    //Блокируем другие картинки
    public void toBlockOtherImages(ImageView image1, ImageView image2, ImageView image3){
        image1.setEnabled(false);
        image2.setEnabled(false);
        image3.setEnabled(false);
    }

    //Нажать на картинку
    public  void toClickImage (MotionEvent event, ImageView imageCurrent, ImageView imgOth1,
                               ImageView imgOth2, ImageView imgOth3, ImageView imageLUp, ImageView imageRUp,
                               ImageView imageLLow, ImageView imageRLow, TextView textLUp, TextView textRUp,
                               TextView textLLow, TextView textRLow, Animation a, int[] progress,
                               TextView text, int number) {
        //Условие касания картинки
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //Если коснулся картинки
            //Блокируем другие картинки
            toBlockOtherImages(imgOth1, imgOth2, imgOth3);
            if (number == i) {
                imageCurrent.setImageResource(R.drawable.yes);
            } else {
                imageCurrent.setImageResource(R.drawable.no);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //Если отпустил палец
            if (number == i) {
                //Определяем правильные ответы и закрашиваем зеленым
                TextView tv = findViewById(progress[count]);
                tv.setBackgroundResource(R.drawable.style_points_green);
                count++;
                listLearned.add(i);
            }
            if (count == 10) {
                //Выход из уровня
                dialogEnd.show();
            } else {
                getImages(imageLUp, imageRUp, imageLLow, imageRLow, textLUp, textRUp, textLLow, textRLow,
                        array.images18, array.texts18_2);
                imageLUp.startAnimation(a);
                imageRUp.startAnimation(a);
                imageLLow.startAnimation(a);
                imageRLow.startAnimation(a);
                //Разблокируем другие картинки
                toUnblockOtherImages(imgOth1, imgOth2, imgOth3);
                //Определяем спрашиваемое слово на английском
                getWordEnglish(numLeftUpper, numLeftLower, numRightUpper, numRightLower, text, array.texts18_1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_10_qst);

        //Создаем переменную text_levels
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level18); //Установили текст

        final ImageView img_left_upper = (ImageView) findViewById(R.id.img_left_upper);
        //Скругление углов на картинках
        img_left_upper.setClipToOutline(true);

        final ImageView img_right_upper = (ImageView) findViewById(R.id.img_right_upper);
        //Скругление углов на картинках
        img_right_upper.setClipToOutline(true);

        final ImageView img_left_lower = (ImageView) findViewById(R.id.img_left_lower);
        //Скругление углов на картинках
        img_left_lower.setClipToOutline(true);

        final ImageView img_right_lower = (ImageView) findViewById(R.id.img_right_lower);
        //Скругление углов на картинках
        img_right_lower.setClipToOutline(true);

        //Путь к тексту слова на английском языке
        final TextView text = findViewById(R.id.text);

        //Путь к переводу
        final TextView text_left_upper = findViewById(R.id.text_left_upper);
        final TextView text_right_upper = findViewById(R.id.text_right_upper);
        final TextView text_left_lower = findViewById(R.id.text_left_lower);
        final TextView text_right_lower = findViewById(R.id.text_right_lower);

        //Путь к кнопке звука
        final ImageView pronunciationBtn = findViewById(R.id.imageViewPronunciation);

        //Развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Диалоговое окно в конце уроовня
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.dialog_end);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.setCancelable(false);

        //Возврат в меню выбора уровня
        TextView closeBtn = (TextView) dialogEnd.findViewById(R.id.buttonClose);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Level18.this, Levels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
                dialogEnd.dismiss();
            }
        });

        //Переход на следующий уровень
        Button continueBtn = (Button) dialogEnd.findViewById(R.id.buttonContinue);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Level18.this, Level19.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
                dialogEnd.dismiss();
            }
        });

        //Кнопка назад уровня
        Button backBtn = (Button) findViewById(R.id.buttonBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Level18.this, Levels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
            }
        });

        //Массив для прогресса обучения
        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
                R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
        };

        //Подключаем анимацию
        final Animation a = AnimationUtils.loadAnimation(Level18.this, R.anim.alpha);

        //Достаем картинку и перевод из массива
        getImages(img_left_upper, img_right_upper, img_left_lower, img_right_lower, text_left_upper,
                text_right_upper, text_left_lower, text_right_lower, array.images18, array.texts18_2);

        //Определяем спрашиваемое слово на английском
        getWordEnglish(numLeftUpper, numLeftLower, numRightUpper, numRightLower, text, array.texts18_1);

        //Определяем произношение
        pronunciationBtnClick(pronunciationBtn);

        //Нажатие на левую верхнюю картинку
        img_left_upper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toClickImage(event, img_left_upper, img_right_upper, img_left_lower, img_right_lower,
                        img_left_upper, img_right_upper, img_left_lower, img_right_lower,
                        text_left_upper, text_right_upper, text_left_lower, text_right_lower,
                        a, progress, text, numLeftUpper);
                return true;
            }

        });

        //Нажатие на правую верхнюю картинку
        img_right_upper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toClickImage(event, img_right_upper, img_left_upper, img_left_lower, img_right_lower,
                        img_left_upper, img_right_upper, img_left_lower, img_right_lower,
                        text_left_upper, text_right_upper, text_left_lower, text_right_lower,
                        a, progress, text, numRightUpper);
                return true;
            }

        });

        //Нажатие на левую нижнюю картинку
        img_left_lower.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toClickImage(event, img_left_lower, img_left_upper, img_right_upper, img_right_lower,
                        img_left_upper, img_right_upper, img_left_lower, img_right_lower,
                        text_left_upper, text_right_upper, text_left_lower, text_right_lower,
                        a, progress, text, numLeftLower);
                return true;
            }

        });

        //Нажатие на правую нижнюю картинку
        img_right_lower.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toClickImage(event, img_right_lower, img_left_upper, img_right_upper, img_left_lower,
                        img_left_upper, img_right_upper, img_left_lower, img_right_lower,
                        text_left_upper, text_right_upper, text_left_lower, text_right_lower,
                        a, progress, text, numRightLower);
                return true;
            }
        });
    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Level18.this, Levels.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }
}
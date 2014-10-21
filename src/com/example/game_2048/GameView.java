package com.example.game_2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	private Card[][] cardMapCards = new Card[4][4];
	private List<Point> emptyPoints = new ArrayList<Point>();

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	private void initGameView() {

		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		setOnTouchListener(new OnTouchListener() {

			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;

					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX > 5) {
							System.out.println("right");
							swipeRight();
						} else if (offsetX < -5) {
							System.out.println("left");
							swipeLeft();
						}
					} else {
						if (offsetY > 5) {
							System.out.println("down");
							swipeDown();
						} else if (offsetY < -5) {
							System.out.println("up");
							swipeUp();
						}
					}

					break;
				}
				return true;
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cradWidth = (Math.min(w, h) - 10) / 4;
		addCards(cradWidth, cradWidth);

		StartGame();
	}

	private void addCards(int cardWidth, int cardHeight) {

		Card card;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				card = new Card(getContext());
				card.setNum(0);
				addView(card, cardWidth, cardHeight);

				cardMapCards[x][y] = card;
			}
		}
	}

	private void StartGame() {

		MainActivity.getMainActivity().clearScore();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardMapCards[x][y].setNum(0);

			}
		}
		addRandomNum();
		addRandomNum();

	}

	private void addRandomNum() {

		emptyPoints.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMapCards[x][y].getNum() <= 0) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}

		Point point = emptyPoints.remove((int) (Math.random() * emptyPoints
				.size()));
		cardMapCards[point.x][point.y].setNum(Math.random() > 0.1 ? 2 : 4);
	}

	private void swipeLeft() {

		boolean merge = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cardMapCards[x1][y].getNum() > 0) {
						if (cardMapCards[x][y].getNum() <= 0) {
							cardMapCards[x][y].setNum(cardMapCards[x1][y]
									.getNum());
							cardMapCards[x1][y].setNum(0);

							x--;
							merge = true;
						} else if (cardMapCards[x][y]
								.equals(cardMapCards[x1][y])) {
							cardMapCards[x][y].setNum(cardMapCards[x][y]
									.getNum() * 2);
							cardMapCards[x1][y].setNum(0);

							MainActivity.getMainActivity().addScore(
									cardMapCards[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeRight() {
		boolean merge = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cardMapCards[x1][y].getNum() > 0) {
						if (cardMapCards[x][y].getNum() <= 0) {
							cardMapCards[x][y].setNum(cardMapCards[x1][y]
									.getNum());
							cardMapCards[x1][y].setNum(0);

							x++;
							merge = true;
						} else if (cardMapCards[x][y]
								.equals(cardMapCards[x1][y])) {
							cardMapCards[x][y].setNum(cardMapCards[x][y]
									.getNum() * 2);
							cardMapCards[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(
									cardMapCards[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeUp() {
		boolean merge = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y + 1; y1 < 4; y1++) {
					if (cardMapCards[x][y1].getNum() > 0) {
						if (cardMapCards[x][y].getNum() <= 0) {
							cardMapCards[x][y].setNum(cardMapCards[x][y1]
									.getNum());
							cardMapCards[x][y1].setNum(0);

							y--;
							merge = true;
						} else if (cardMapCards[x][y]
								.equals(cardMapCards[x][y1])) {
							cardMapCards[x][y].setNum(cardMapCards[x][y]
									.getNum() * 2);
							cardMapCards[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(
									cardMapCards[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeDown() {
		boolean merge = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (cardMapCards[x][y1].getNum() > 0) {
						if (cardMapCards[x][y].getNum() <= 0) {
							cardMapCards[x][y].setNum(cardMapCards[x][y1]
									.getNum());
							cardMapCards[x][y1].setNum(0);

							y++;
							merge = true;
						} else if (cardMapCards[x][y]
								.equals(cardMapCards[x][y1])) {
							cardMapCards[x][y].setNum(cardMapCards[x][y]
									.getNum() * 2);
							cardMapCards[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(
									cardMapCards[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	private void checkComplete() {

		boolean complete = true;

		All: for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMapCards[x][y].getNum() <= 0
						|| ((x > 0) && cardMapCards[x][y]
								.equals(cardMapCards[x - 1][y]))
						|| ((x < 3) && cardMapCards[x][y]
								.equals(cardMapCards[x + 1][y]))
						|| ((y > 0) && cardMapCards[x][y]
								.equals(cardMapCards[x][y - 1]))
						|| ((y < 3) && cardMapCards[x][y]
								.equals(cardMapCards[x][y + 1]))) {
					complete = false;
					break All;
				}
			}
		}

		if (complete) {
			new AlertDialog.Builder(getContext())
					.setTitle("你好")
					.setMessage("游戏结束")
					.setPositiveButton("重来",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									StartGame();
								}
							}).show();
		}
	}
}

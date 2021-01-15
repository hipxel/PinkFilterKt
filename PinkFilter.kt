/*
 * MIT License
 *
 * Copyright (c) 2018 Dima Yv <dfcreative@gmail.com>
 * Copyright (c) 2021 Hipxel <hipxel@gmail.com> (Port)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Ported from: https://github.com/audiojs/audio-noise/blob/master/index.js
 */

interface ValuesGenerator {
	fun nextValue(): Double
}

/**
 * Generates pink noise from white noise.
 * @param inNoise input values generator, f.e. Random#random() for white noise
 * @param m input multiplier
 */
class PinkFilter(
		private val inNoise: ValuesGenerator,
		private val m: Double
) : ValuesGenerator {
	private val b = DoubleArray(7)

	override fun nextValue(): Double {
		val x = inNoise.nextValue() * m
		b[0] = 0.99886 * b[0] + x * 0.0555179
		b[1] = 0.99332 * b[1] + x * 0.0750759
		b[2] = 0.96900 * b[2] + x * 0.1538520
		b[3] = 0.86650 * b[3] + x * 0.3104856
		b[4] = 0.55000 * b[4] + x * 0.5329522
		b[5] = -0.7616 * b[5] - x * 0.0168980

		var r = b[0] + b[1] + b[2] + b[3] + b[4] + b[5] + b[6] + x * 0.5362
		r *= 0.11
		b[6] = x * 0.115926

		return r
	}
}
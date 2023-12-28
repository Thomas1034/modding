package com.thomas.zirconmod.util;

import java.util.List;
import java.util.stream.Collectors;

import com.thomas.zirconmod.enchantment.ModEnchantments;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Utilities {
	public static void addParticlesAroundEntity(Entity entity, ParticleOptions particle) {
		addParticlesAroundEntity(entity, particle, 1.0);
	}

	public static void addParticlesAroundEntity(Entity entity, ParticleOptions particle, double boxSize) {
		Level level = entity.level();
		addParticlesAroundPosition(level, entity.getEyePosition(), particle, boxSize);
	}

	public static void addParticlesAroundPosition(Level level, Vec3 position, ParticleOptions particle,
			double boxSize) {
		for (int i = 0; i < 5; ++i) {
			double dx = level.random.nextGaussian() * 0.02D;
			double dy = level.random.nextGaussian() * 0.02D;
			double dz = level.random.nextGaussian() * 0.02D;
			double x = (2.0D * level.random.nextDouble() - 1.0D) * boxSize + position.x;
			double y = (2.0D * level.random.nextDouble() - 1.0D) * boxSize + position.y;
			double z = (2.0D * level.random.nextDouble() - 1.0D) * boxSize + position.z;
			level.addParticle(particle, x, y, z, dx, dy, dz);
		}
	}

	// Rotates the given x, y, z position so that z-vector is aligned with the
	// facing vector.
	public static Vec3 localCoordinates(Vec3 at, Vec3 facing, Vec3 offset) {

		// Calculate the end position of z.
		facing = facing.normalize();

		// Calculate the rotation matrix
		double[][] rotationMatrix = Matrix.calculateRotationMatrix(facing, new Vec3(0, 0, 1));

		// Calculate the final position.
		Vec3 endPos = Matrix.matToVec(Matrix.multiplyMatrix(rotationMatrix, Matrix.vecToMat(offset)));

		return at.add(endPos);
	}

	// Written by ChatGPT
	public static class Matrix {

		public static double[][] calculateRotationMatrix(Vec3 originalVector, Vec3 targetVector) {
			// Step 1: Calculate the Axis of Rotation
			Vec3 axis = originalVector.cross(targetVector).normalize();

			// Step 2: Calculate the Angle of Rotation
			double angle = Math
					.acos(originalVector.dot(targetVector) / (originalVector.length() * targetVector.length()));

			// Step 3: Construct the Rotation Matrix
			return calculateRotationMatrix(axis, angle);
		}

		public static double[][] calculateRotationMatrix(Vec3 axis, double angle) {
			double cosTheta = Math.cos(angle);
			double sinTheta = Math.sin(angle);

			return new double[][] {
					{ cosTheta + axis.x * axis.x * (1 - cosTheta), axis.x * axis.y * (1 - cosTheta) - axis.z * sinTheta,
							axis.x * axis.z * (1 - cosTheta) + axis.y * sinTheta },
					{ axis.y * axis.x * (1 - cosTheta) + axis.z * sinTheta, cosTheta + axis.x * axis.y * (1 - cosTheta),
							axis.y * axis.z * (1 - cosTheta) - axis.x * sinTheta },
					{ axis.z * axis.x * (1 - cosTheta) - axis.y * sinTheta,
							axis.z * axis.y * (1 - cosTheta) + axis.x * sinTheta,
							cosTheta + axis.z * axis.z * (1 - cosTheta) } };
		}

		public static Vec3 matToVec(double[][] arr) {
			return new Vec3(arr[0][0], arr[1][0], arr[2][0]);
		}

		public static double[][] vecToMat(Vec3 vec) {
			return new double[][] { new double[] { vec.x }, new double[] { vec.y }, new double[] { vec.z } };
		}

		// Written by ChatGPT
		public static double[][] multiplyMatrix(double[][] matrixA, double[][] matrixB) {
			int rowsA = matrixA.length;
			int colsA = matrixA[0].length;
			int colsB = matrixB[0].length;

			if (matrixB.length != colsA) {
				throw new IllegalArgumentException("Incompatible matrix dimensions for multiplication");
			}

			double[][] result = new double[rowsA][colsB];

			for (int i = 0; i < rowsA; i++) {
				for (int j = 0; j < colsB; j++) {
					for (int k = 0; k < colsA; k++) {
						result[i][j] += matrixA[i][k] * matrixB[k][j];
					}
				}
			}

			return result;

		}

	}

}
